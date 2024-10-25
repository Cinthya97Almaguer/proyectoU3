package edu.itq.soa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.ResponseDeposito;
import edu.itq.soa.jms.JmsSender;

/**
 * Clase que se encarga de manejar la lógica de negocio de las peticiones.
 */
@Component
public class RequestBusiness {

    @Autowired
    private JmsSender jmsSender;

    public void execute(JmsMessage jmsMessage) {
        Gson gson = new Gson();
        Request request = gson.fromJson(jmsMessage.getMessage(), Request.class);

        // Calcular el saldo actual sumando el monto del depósito al saldo existente
        //mandando llamar request
        double saldoActual = Double.parseDouble(request.monto());

        // Crear la respuesta con el mensaje "Deposito hecho" y el saldo actualizado
        ResponseDeposito responseSaldo = new ResponseDeposito(
                request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                request.numeroTarjeta(), request.numeroCuenta(), request.tasa(), 
                request.plazo(), request.monto(), request.historial(), request.Credito(), request.pd(),
                request.pagoMensual(), request.interes(), request.capital(), 
                String.valueOf(saldoActual), request.total(), "Deposito hecho", String.valueOf(saldoActual));

        // Convertir la respuesta a JSON y enviarla por JMS
        JmsMessage jmsMessageSaldo = new JmsMessage(responseSaldo.toString(), jmsMessage.getProperties());
        jmsSender.send("deposito.out", jmsMessageSaldo);
    }
}