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
 */
public class JmsMessage {
    /** Prefijo para descartar en las propiedades. */
    private static final String JMS = "JMS";

    /** Payload del mensaje (cuerpo). */
    private String message; 

    /** Propiedades del mensaje. */
    Map<String, String> properties = new HashMap<>();

    /**
     * Constructor del DTO basado en un mensaje.
     * @param textMessage Mensaje JMS.
     */
    public JmsMessage(TextMessage textMessage) {
        try {
            this.message = textMessage.getText();
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
    }

    /**
     * Constructor del DTO basado en un mensaje.
     * @param json       Mensaje.
     * @param properties Propiedades.
     */
    public JmsMessage(String json, Map<String, String> properties) {
        this.message = json;
        this.properties = properties;
    }

    /**
     * @return the message
     */
    public final String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public final void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the properties
     */
    public final Map<String, String> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public final void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /** Representación String del objeto. */
    @Override
    public String toString() {
        return "JmsMessage [message=" + message + ", properties=" + properties + "]";
    }
}
