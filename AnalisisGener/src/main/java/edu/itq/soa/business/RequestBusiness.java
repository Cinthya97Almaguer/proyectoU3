package edu.itq.soa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.Response;
import edu.itq.soa.jms.JmsSender;

@Component
public class RequestBusiness {

    @Autowired
    private JmsSender jmsSender;

    public void execute(JmsMessage jmsMessage) {
        Gson gson = new Gson();
        Request request = gson.fromJson(jmsMessage.getMessage(), Request.class);

        // Convertir la tasa a un valor decimal y calcular el pago mensual
        double tasa = Double.parseDouble(request.tasa()) / 100;
        int plazo = Integer.parseInt(request.plazo());
        double monto = Double.parseDouble(request.monto());

        double pagoMensual = calcularPagoMensual(monto, tasa, plazo);

        // Generar la tabla de amortización
        List<Amortizacion> tablaAmortizacion = generarTablaAmortizacion(monto, tasa, plazo, pagoMensual);

        // Crear la respuesta incluyendo la tabla
        Response response = new Response(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.numeroTarjeta(),
                request.numeroCuenta(),
                request.tasa(),
                request.plazo(),
                request.monto(),
                request.historial(),
                request.Credito(),
                "Tabla de amortización generada", // pd
                String.valueOf(pagoMensual), 
                null, // interes
                null, // capital
                null, // saldoActual
                null  // total
            );

        // Convertir a JSON y enviar el mensaje
        JmsMessage jmsMessageSaldo = new JmsMessage(gson.toJson(response), jmsMessage.getProperties());
        jmsSender.send("tabla.out", jmsMessageSaldo);

        // Enviar la tabla completa a la cola "tabla.out"
        enviarTablaAmortizacion(tablaAmortizacion, jmsMessage);
    }

    private double calcularPagoMensual(double monto, double tasa, int plazo) {
        return (monto * tasa) / (1 - Math.pow(1 + tasa, -plazo));
    }

    private List<Amortizacion> generarTablaAmortizacion(double monto, double tasa, int plazo, double pagoMensual) {
        List<Amortizacion> tabla = new ArrayList<>();
        double saldo = monto;

        for (int i = 1; i <= plazo; i++) {
            double interes = saldo * tasa;
            double capital = pagoMensual - interes;
            saldo -= capital;

            tabla.add(new Amortizacion(i, pagoMensual, interes, capital, saldo));
        }

        return tabla;
    }

    // Método para enviar la tabla a la cola "tabla.out"
    private void enviarTablaAmortizacion(List<Amortizacion> tablaAmortizacion, JmsMessage jmsMessage) {
        Gson gson = new Gson();
        StringBuilder tablaString = new StringBuilder();

        // Crear la cabecera de la tabla
        tablaString.append("Periodo | Pago Mensual | Interés | Capital | Saldo\n");
        tablaString.append("-------------------------------------------------\n");

        // Añadir cada fila de la tabla de amortización
        for (Amortizacion amortizacion : tablaAmortizacion) {
            tablaString.append(amortizacion.getPeriodo()).append(" | ")
                .append(String.format("%.2f", amortizacion.getPagoMensual())).append(" | ")
                .append(String.format("%.2f", amortizacion.getInteres())).append(" | ")
                .append(String.format("%.2f", amortizacion.getCapital())).append(" | ")
                .append(String.format("%.2f", amortizacion.getSaldo())).append("\n");
        }

        // Crear el mensaje JMS y enviar la tabla formateada
        JmsMessage jmsMessageTabla = new JmsMessage(tablaString.toString(), jmsMessage.getProperties());
        jmsSender.send("tabla.out", jmsMessageTabla);
    }
}
