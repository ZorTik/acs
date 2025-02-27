package me.zort.acs.subject;

import me.zort.acs.exception.AccessGrantException;
import me.zort.acs.exception.ScopeResourceBasedException;
import me.zort.acs.exception.UnsupportedAccess;
import me.zort.acs.scope.Scope;
import me.zort.acs.scope.Scoped;

public interface Subject extends Scoped {

    String getId();

    @Override
    Scope getScope();

    default void ensureAccess(Scoped scoped, boolean isSubjectAccess) throws AccessGrantException {
        Scope scope = scoped.getScope();

        if (isSubjectAccess != scope.isSubjectBased()) {
            throw new ScopeResourceBasedException(scope);
        }

        if (!scope.getAllowedAccessors().contains(this.getScope())) {
            throw new UnsupportedAccess(this, scope);
        }

        // Valid access check
    }
}
