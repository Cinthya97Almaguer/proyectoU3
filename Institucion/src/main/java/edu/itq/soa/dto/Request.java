package edu.itq.soa.dto;

/**
 * DTO para las peticiones recibidas.
 */
public record Request(String nombre, String apellidoPaterno, String apellidoMaterno,
        String numeroTarjeta, String numeroCuenta,
        String tasa, String plazo, String monto) {

	public Object Credito() {
		// TODO Auto-generated method stub
		return null;
	}
}

/**
 * DTO que representa un domicilio.
 */
