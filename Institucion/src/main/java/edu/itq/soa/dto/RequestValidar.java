package edu.itq.soa.dto;

/**
 * DTO para las peticiones recibidas.
 */
public record RequestValidar(String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto, String historial)
{

}

/*
{
    "numeroTelefonico": "1234567890"
}
*/