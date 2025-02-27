package me.zort.acs.exception;

import lombok.Getter;
import me.zort.acs.scope.Scope;

@Getter
public class ScopeResourceBasedException extends AccessGrantException {
    private final Scope scope;

    public ScopeResourceBasedException(Scope scope) {
        super("Scope is not resource based!");
        this.scope = scope;
    }
}
