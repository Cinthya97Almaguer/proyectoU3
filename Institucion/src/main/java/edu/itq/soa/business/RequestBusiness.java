/**
 * Copyright ITQ 2024.
 */
package edu.itq.soa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.RequestValidar;
import edu.itq.soa.jms.JmsSender;

/**
 * Clase que se encarga de manejar la lógica de negocio de las peticiones.
 */
@Component
public class RequestBusiness {

    /** Objeto para mandar mensajes JMS. */
    @Autowired
    private JmsSender jmsSender;

    public void execute(JmsMessage jmsMessage) {
        Gson gson = new Gson();
        Request request = gson.fromJson(jmsMessage.getMessage(), Request.class);
        RequestValidar requestSaldo = new RequestValidar(request.numeroTarjeta());
//        String saldoJson = "{\"numeroTelefononico\": \"" + request.numeroTelefonico() + "\"}";
        String saldoJson = gson.toJson(requestSaldo);
        JmsMessage jmsMessageSaldo = new JmsMessage(saldoJson, jmsMessage.getProperties());
        jmsSender.send("validar.in", jmsMessageSaldo);
    }
}
