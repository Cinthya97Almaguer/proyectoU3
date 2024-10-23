/**
 * 
 */
package com.soa.tools;

/**
 * Clase que controla el flujo del programa para escenarios espciales de fallo.
 */
public class SOAException extends RuntimeException {

    /** Código asociado a la exception */
    private int code;

    /**
     * 
     */
    public SOAException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public SOAException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * @param message
     */
    public SOAException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SOAException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public SOAException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SOAException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the code
     */
    public final int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public final void setCode(int code) {
        this.code = code;
    }

}
