/**
 * Copyright ITQ 2024.
 */
package edu.itq.soa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import edu.itq.soa.dao.SaldoDao;
import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.dto.Request;
import edu.itq.soa.dto.ResponseBuro;
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

         // Convertir historial a número para hacer la comparación
         int historial = Integer.parseInt(request.historial());

         // Determinar el estado del crédito
         String buenHistorial;
         if (historial > 75) {
             buenHistorial = "CREDITO PREAUTORIZADO";
         } else {
             buenHistorial = "CREDITO RECHAZADO";
         }

         // Crear la respuesta con el resultado
         ResponseBuro responseSaldo = new ResponseBuro(
                 request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                 request.numeroTarjeta(), request.numeroCuenta(), request.tasa(), 
                 request.plazo(), request.monto(), request.historial(), buenHistorial);

         // Convertir la respuesta a JSON y enviarla por JMS
         JmsMessage jmsMessageSaldo = new JmsMessage(responseSaldo.toString(), jmsMessage.getProperties());
         jmsSender.send("validar.out", jmsMessageSaldo);
         
     }
 }