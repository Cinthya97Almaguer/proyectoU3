package edu.itq.soa.business;

public class Amortizacion {
    private int numeroPago;
    private double pagoMensual;
    private double interes;
    private double capital;
    private double saldoRestante;

    public Amortizacion(int numeroPago, double pagoMensual, double interes, double capital, double saldoRestante) {
        this.numeroPago = numeroPago;
        this.pagoMensual = pagoMensual;
        this.interes = interes;
        this.capital = capital;
        this.saldoRestante = saldoRestante;
    }

    // Getters y Setters (opcional si usas Lombok)
}

