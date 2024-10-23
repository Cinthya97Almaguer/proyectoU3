/**
 * ITQ Copyrigth 2024.
 */
package edu.itq.soa.dto;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.soa.tools.SOAException;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

/**
 * Clase para representar un mensaje JMS.
 * Payload del mensaje (cuerpo).
 * Propiedades del mensaje.
 */
public record JmsMessageRec(String message, Map<String, String> properties) {
    /** Prefijo para descartar en las propiedades. */
    private static final String JMS = "JMS";

    /**
     * Constructor del DTO basado en un mensaje.
     * @param textMessage Mensaje JMS.
     */
    public JmsMessageRec(TextMessage textMessage) {
        this(extractPayload(textMessage), extractProperties(textMessage));
    }

    private static String extractPayload(TextMessage textMessage) {
        try {
            return textMessage.getText();
        } catch (JMSException e) {
            throw new SOAException("Error al leer el mensaje JMS", e);
        }
    }
    
    private static Map<String, String> extractProperties(TextMessage textMessage) {
        Map<String, String> properties = new HashMap<>();
        try {
            Enumeration<?> propertyNames = textMessage.getPropertyNames();
            while (propertyNames.hasMoreElements()) {
                String propertyName = (String) propertyNames.nextElement();
                if (propertyName.startsWith(JMS)) {
                    continue;
                }
                String propertyValue = textMessage.getStringProperty(propertyName);
                properties.put(propertyName, propertyValue);
            }
        } catch (JMSException e) {
            throw new SOAException("Error al leer el mensaje JMS", e);
        }
        return properties;
    }
    
    /** Representación String del objeto. */
    @Override
    public String toString() {
        return "JmsMessage [message=" + message + ", properties=" + properties + "]";
    }
}
