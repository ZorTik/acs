package me.zort.acs.exception;

public abstract class AccessGrantException extends Exception {

    public AccessGrantException() {
    }

    public AccessGrantException(String message) {
        super(message);
    }

    public AccessGrantException(String message, Throwable cause) {
        super(message, cause);
    }
}
