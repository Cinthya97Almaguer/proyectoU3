/**
 * Copyright ITQ 2024.
 */
package edu.itq.soa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import java.util.Random;

import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.RequestValidar;
import edu.itq.soa.jms.JmsSender;

/**
 * Clase que se encarga de manejar la lógica de negocio de las peticiones.
 */
@Component
public class GenerarBusiness {

    /** Objeto para mandar mensajes JMS. */
    @Autowired
    private JmsSender jmsSender;
    
    private final Random random = new Random(); 

    public void execute(JmsMessage jmsMessage) {
        Gson gson = new Gson();
        Request request = gson.fromJson(jmsMessage.getMessage(), Request.class);
        
     // Validar el estado del crédito
        if ("PREAUTORIZADO".equals(request.Credito())) {
            // Continuar con el proceso si el crédito está preautorizado
            String saldoJson = generarJsonDeSaldo(request); // Método ficticio que genera el JSON de saldo

            // Crear un nuevo mensaje JMS con el saldo generado
            JmsMessage jmsMessageSaldo = new JmsMessage(saldoJson, jmsMessage.getProperties());
            
            // Enviar el mensaje a la cola "tabla.out"
            jmsSender.send("tabla.out", jmsMessageSaldo);

        } else if ("RECHAZADO".equals(request.Credito())) {
            // Enviar a la cola "credito.out" si el crédito es rechazado
            String rechazoJson = generarJsonDeRechazo(request); // Método ficticio que genera el JSON de rechazo

            // Crear un nuevo mensaje JMS con el rechazo
            JmsMessage jmsMessageRechazo = new JmsMessage(rechazoJson, jmsMessage.getProperties());
            
            // Enviar el mensaje a la cola "credito.out"
            jmsSender.send("credito.out", jmsMessageRechazo);
        }
    }

    // Método ficticio que genera el JSON de saldo basado en el objeto Request
    private String generarJsonDeSaldo(Request request) {
        // Aquí generas el JSON de saldo según tus reglas de negocio
        return "{\"status\":\"preautorizado\", \"detalle\":\"Saldo generado correctamente\"}";
    }

    // Método ficticio que genera el JSON de rechazo basado en el objeto Request
    private String generarJsonDeRechazo(Request request) {
        // Aquí generas el JSON de rechazo según tus reglas de negocio
        return "{\"status\":\"rechazado\", \"detalle\":\"Crédito rechazado\"}";
    }
}