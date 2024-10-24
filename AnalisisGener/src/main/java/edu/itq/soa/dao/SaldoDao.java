/**
 * 
 */
package edu.itq.soa.dao;

import java.util.Map;

/**
 * Clase que implementa el patrón DAO.
 */
public class SaldoDao {
    Map<String, Double> bd = Map.of("1234567890", 100.0, 
            "0987654321", 200.0, 
            "111111111", 120.0);

    /**
     * Método que busca el saldo asociado a un número telefónico.
     * @param numeroTelefonico Número telefónico a buscar.
     * @return Saldo asociado al número telefónico.
     */
    public Double findByPhoneNumber(String numeroTelefonico) {
        if (bd.containsKey(numeroTelefonico)) {
            return bd.get(numeroTelefonico); // Saldo asociado al número telefónico.
        }
        return 0.0;
    }

}
