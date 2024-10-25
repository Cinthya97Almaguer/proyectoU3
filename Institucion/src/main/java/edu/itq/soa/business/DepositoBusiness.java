/**
 * Copyright ITQ 2024.
 */
package edu.itq.soa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.itq.soa.dto.JmsMessage;
import edu.itq.soa.jms.JmsSender;

/**
 * Clase que se encarga de manejar la lógica de negocio de las peticiones.
 */
@Component
public class DepositoBusiness {

    /** Objeto para mandar mensajes JMS. */
    @Autowired
    private JmsSender jmsSender;

    /**
     * Método que se encarga de manejar la lógica de negocio de las peticiones.
     * @param jmsMessage Mensaje de respuesta del componente de saldo.
     */
    public void execute(JmsMessage jmsMessage) {
        JmsMessage jmsMessageSaldo = new JmsMessage(jmsMessage.getMessage(), jmsMessage.getProperties());
        jmsSender.send("deposito.in", jmsMessageSaldo);
    }
}
