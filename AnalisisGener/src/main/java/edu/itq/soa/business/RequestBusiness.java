package edu.itq.soa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.Response;
import edu.itq.soa.dto.ResponseBuro;
import edu.itq.soa.jms.JmsSender;

@Component
public class RequestBusiness {

    @Autowired
    private JmsSender jmsSender;

    public void execute(JmsMessage jmsMessage) {
        Gson gson = new Gson();
        Request request = gson.fromJson(jmsMessage.getMessage(), Request.class);

        // Convertir la tasa a decimal y calcular el pago mensual
        double tasa = Double.parseDouble(request.tasa()) / 100;
        int plazo = Integer.parseInt(request.plazo());
        double monto = Double.parseDouble(request.monto());
        
     // Crear la respuesta con el resultado
        Response responseSaldo = new Response(
                request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                request.numeroTarjeta(), request.numeroCuenta(), request.tasa(), 
                request.plazo(), request.monto(), request.historial(),request.pd());

        // Convertir la respuesta a JSON y enviarla por JMS
        JmsMessage jmsMessageSaldo = new JmsMessage(responseSaldo.toString(), jmsMessage.getProperties());
        jmsSender.send("tabla.out", jmsMessageSaldo);
        
        
        //double pagoMensual = calcularPagoMensual(monto, tasa, plazo);

        /**
        // Generar la tabla de amortización
        List<Amortizacion> tablaAmortizacion = generarTablaAmortizacion(monto, tasa, plazo, pagoMensual);

        // Tomar los valores de la primera fila de la tabla como ejemplo para la respuesta
        Amortizacion primeraFila = tablaAmortizacion.get(0);
        double interes = (double) primeraFila.getInteres();
        double capital = (double) primeraFila.getCapital();
        double saldoActual = (double) primeraFila.getSaldo();
        double totalPagado = pagoMensual * plazo;

        // Crear la respuesta incluyendo la tabla de amortización
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
                "Tabla de amortización generada",  // pd
                String.valueOf(pagoMensual),       // Pago mensual
                String.valueOf(interes),           // Interés del primer periodo
                String.valueOf(capital),           // Capital del primer periodo
                String.valueOf(saldoActual),       // Saldo actual del primer periodo
                String.valueOf(totalPagado)        // Total pagado al final del plazo
        );

        // Convertir a JSON y enviar el mensaje
        JmsMessage jmsMessageSaldo = new JmsMessage(gson.toJson(response), jmsMessage.getProperties());
        jmsSender.send("tabla.out", jmsMessageSaldo);

        // Enviar la tabla completa a la cola "tabla.out"
        enviarTablaAmortizacion(tablaAmortizacion, jmsMessage);
    }

    // Método para calcular el pago mensual
    private double calcularPagoMensual(double monto, double tasa, int plazo) {
        return (monto * tasa) / (1 - Math.pow(1 + tasa, -plazo));
    }

    // Método para generar la tabla de amortización
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

    // Método para enviar la tabla de amortización completa a la cola "tabla.out"
    private void enviarTablaAmortizacion(List<Amortizacion> tablaAmortizacion, JmsMessage jmsMessage) {
        Gson gson = new Gson();
        StringBuilder tablaString = new StringBuilder();

        // Crear la cabecera de la tabla (opcional)
        tablaString.append("Periodo | Pago Mensual | Interés | Capital | Saldo Restante\n");
        tablaString.append("----------------------------------------------------------\n");

        // Agregar cada fila de la tabla al StringBuilder
        for (Amortizacion fila : tablaAmortizacion) {
            tablaString.append(String.format("%d | %.2f | %.2f | %.2f | %.2f\n",
                    fila.pd(), fila.getPagoMensual(), fila.getInteres(),
                    fila.getCapital(), fila.getSaldo()));
        }
         * 
         */
        
         
    }
}
