package edu.itq.soa.dto;

/**
 * DTO para las peticiones recibidas.
 */
public record RequestDeposito (String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto, String buroCredito,
        String pd,String pagoMensual, String interes, String Capital) {
    
}
