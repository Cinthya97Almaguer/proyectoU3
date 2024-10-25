package edu.itq.soa.dto;

import com.google.gson.Gson;

/**
 * DTO para las peticiones recibidas.
 */
public record ResponseDeposito (String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto, String historial, String Credito,
        String pd, String pagoMensual, String interes, String capital, String saldoActual, String total ,
        String realizado, String saldo) {

    /** Representación en formato JSON del objeto actual. */
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

/*
{
    "numeroTelefonico": "1234567890"
}
*/