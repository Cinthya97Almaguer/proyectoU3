/**
 * ITQ Copyrigth 2024.
 */
package edu.itq.soa.dto;

/**
 * Clase que representa un DTO de respuesta. inmutable.
 */
public record ResponseGenerar (String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto, String buroCredito,
        String pd,String pagoMensual, String interes, String Capital) {
    
}
