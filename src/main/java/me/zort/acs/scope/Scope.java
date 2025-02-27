package me.zort.acs.scope;

import java.util.Set;

public interface Scope extends Scoped {

    String getId();

    boolean isSubjectBased();

    Set<? extends Scope> getAllowedAccessors();

    @Override
    default Scope getScope() {
        return this;
    }
}
