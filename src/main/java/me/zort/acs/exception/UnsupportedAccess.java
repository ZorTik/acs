package me.zort.acs.exception;

import lombok.Getter;
import me.zort.acs.subject.Subject;
import me.zort.acs.scope.Scope;

@Getter
public class UnsupportedAccess extends AccessGrantException {
    private final Subject subject;
    private final Scope scope;

    public UnsupportedAccess(Subject subject, Scope scope) {
        super(String.format(
                "Actor %s tried to access scope %s, but the target scope " +
                "does not allow this actor's subject.", subject.getScope().getId(), scope.getId()));
        this.subject = subject;
        this.scope = scope;
    }
}
