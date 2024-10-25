/**
 * Copyright ITQ 2024.
 */
package edu.itq.soa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.RequestGenerar;
import edu.itq.soa.jms.JmsSender;

/**
 * Clase que se encarga de manejar la lógica de negocio de las peticiones.
 */
@Component
public class GenerarBusiness {

    /** Objeto para mandar mensajes JMS. */
    @Autowired
    private JmsSender jmsSender;
    

    public void execute(JmsMessage jmsMessage) {
    	 Gson gson = new Gson();
         RequestGenerar request = gson.fromJson(jmsMessage.getMessage(), RequestGenerar.class);
         

         RequestGenerar requestAnalisis = new RequestGenerar(
                 request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                 request.numeroTarjeta(), request.numeroCuenta(), request.tasa(), request.plazo(),
                 request.monto(), request.buroCredito());
         
//         String saldoJson = "{\"numeroTelefononico\": \"" + request.numeroTelefonico() + "\"}";
         String saldoJson = gson.toJson(requestAnalisis);
         JmsMessage jmsMessageSaldo = new JmsMessage(saldoJson, jmsMessage.getProperties());
         jmsSender.send("tabla.in", jmsMessageSaldo);
     }

}