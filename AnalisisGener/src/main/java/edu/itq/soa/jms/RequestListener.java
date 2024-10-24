/**
 * ITQ Copyrigth 2024.
 */
package edu.itq.soa.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.soa.tools.SOAException;

import edu.itq.soa.business.RequestBusiness;
import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

/**
 * Class for listening messages from the queue. P2P: 1 client per 1 message.
 */
@Component
public class RequestListener {

    /** Objeto para manejar la lógica de negocio de las peticiones. */
    @Autowired
    RequestBusiness requestBusiness;

    @JmsListener(destination = "tabla.in")
    public void receive(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            JmsMessage jmsMessage = new JmsMessage(textMessage);
            System.out.println("Message received: " + jmsMessage);
            requestBusiness.execute(jmsMessage);
        } catch (SOAException e) {
            e.printStackTrace();
        }
    }
}
