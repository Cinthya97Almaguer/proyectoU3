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
public class RequestBusiness {

    /** Objeto para mandar mensajes JMS. */
    @Autowired
    private JmsSender jmsSender;
    
    private final Random random = new Random(); 

    public void execute(JmsMessage jmsMessage) {
        Gson gson = new Gson();
        Request request = gson.fromJson(jmsMessage.getMessage(), Request.class);
        

        int historialAleatorio = random.nextInt(50) + 50; 
        
        RequestValidar requesthisorial = new RequestValidar(
                request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                request.numeroTarjeta(), request.numeroCuenta(), request.tasa(), request.plazo(),
                request.monto(), String.valueOf(historialAleatorio)); // Convertir a String
        
        
        
//        String saldoJson = "{\"numeroTelefononico\": \"" + request.numeroTelefonico() + "\"}";
        String saldoJson = gson.toJson(requesthisorial);
        JmsMessage jmsMessageSaldo = new JmsMessage(saldoJson, jmsMessage.getProperties());
        jmsSender.send("validar.in", jmsMessageSaldo);
    }
}
