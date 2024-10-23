package edu.itq.soa.dto;

/**
 * DTO para las peticiones recibidas.
 */
public record Request(String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto) {
}

/**
 * DTO que representa un domicilio.
 */
