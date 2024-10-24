package edu.itq.soa.dto;

/**
 * DTO para las peticiones recibidas.
 */
public record RequestGenerar(String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto, String buroCredito) {
}

/**
 * DTO que representa un domicilio.
 */
