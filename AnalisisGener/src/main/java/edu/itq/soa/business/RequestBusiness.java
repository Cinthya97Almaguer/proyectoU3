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

     // Convertir la tasa a decimal y calcular el pago mensual
        double tasa = Double.parseDouble(request.tasa()) / 100 / 12; // Asumiendo tasa anual
        int plazo = Integer.parseInt(request.plazo());
        double monto = Double.parseDouble(request.monto());

        // Calcular el pago mensual
        double pagoMensual = (monto * tasa) / (1 - Math.pow(1 + tasa, -plazo));

        // Inicializar valores de interés, capital y saldo actual
        double saldoActual = monto;
        double interes = saldoActual * tasa;
        double capital = pagoMensual - interes;
        saldoActual -= capital;

        // Calcular el total (suma de todos los pagos mensuales)
        double total = pagoMensual * plazo;

        // Crear la respuesta con el resultado
        Response responseSaldo = new Response(
                request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                request.numeroTarjeta(), request.numeroCuenta(), request.tasa(), 
                request.plazo(), request.monto(), request.historial(), request.Credito(),
                "12", String.format("%.2f", pagoMensual), String.format("%.2f", interes),
                String.format("%.2f", capital), String.format("%.2f", saldoActual),
                String.format("%.2f", total));

        // Convertir la respuesta a JSON y enviarla por JMS
        JmsMessage jmsMessageSaldo = new JmsMessage(responseSaldo.toString(), jmsMessage.getProperties());
        jmsSender.send("tabla.out", jmsMessageSaldo);
    }
    
}
