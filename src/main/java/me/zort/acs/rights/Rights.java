package me.zort.acs.rights;

import me.zort.acs.scope.Scope;
import me.zort.acs.subject.Subject;

public interface Rights {

    Subject getActor();

    Scope getScope();

    Subject getResource();

    String getNode();
}
