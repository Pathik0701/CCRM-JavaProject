package edu.ccrm.exceptions;

/**
 * Base exception class for CCRM application
 */
public abstract class CCRMException extends Exception {
    public CCRMException(String message) {
        super(message);
    }
    
    public CCRMException(String message, Throwable cause) {
        super(message, cause);
    }
}
