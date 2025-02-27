package me.zort.acs.exception;

public class ScopeNotFoundException extends Exception {

    public ScopeNotFoundException(String scope) {
        super("Scope not found: " + scope);
    }
}
