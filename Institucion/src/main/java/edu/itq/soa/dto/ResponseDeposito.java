package edu.itq.soa.dto;

/**
 * DTO para las peticiones recibidas.
 */
public record ResponseDeposito (String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto, String historial, String Credito,
        String pd, String pagoMensual, String interes, String capital, String saldoActual, String total ,
        String realizado, String saldo) {
}
