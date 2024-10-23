/**
 * ITQ Copyrigth 2024.
 */
package edu.itq.soa.jms;

import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import edu.itq.soa.dto.JmsMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

/**
 * Clase para enviar mensajes JMS a una cola destino.
 */
@Service
public class JmsSender {
    /** plantilla de Spring para enviar mensajes jms. */
    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * Envia un mensaje a la cola destino.
     * 
     * @param cola    Nombre de la cola destino.
     * @param mensaje Mensaje a enviar.
     */
    public void send(String cola, JmsMessage jmsMessage) {
//        ComoTuQuieras obj = new ComoTuQuieras();
//        jmsTemplate.convertAndSend("namejoiner.in", "Hola mundo");
        jmsTemplate.send(cola, new MessageCreator() {
            @Override
            public Message createMessage(@Autowired Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(jmsMessage.getMessage());
                Set<Entry<String,String>> entrySet = 
                        jmsMessage.getProperties().entrySet();
                for (Entry<String, String> entry : entrySet) {
                    textMessage.setStringProperty(entry.getKey(), entry.getValue());
                }
                // List, Map, Set
//                textMessage.setStringProperty("myProperty", "1234");
                return textMessage;
            }
        });
    }
}

//class ComoTuQuieras implements MessageCreator {
//
//    @Override
//    public Message createMessage(Session session) throws JMSException {
//        // TODO Auto-generated method stub
//        return null;
//    }
//    
//}


