/**
 * ITQ Copyrigth 2024.
 */
package edu.itq.soa.dto;

/**
 * Clase que representa un DTO de respuesta. inmutable.
 */
public record Response (String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto, String buroCredito) {
    
}
