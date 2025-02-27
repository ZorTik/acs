package me.zort.acs.rights;

import lombok.Getter;
import me.zort.acs.scope.Scope;
import me.zort.acs.subject.Subject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Getter
public class RightsIdentity implements Rights {
    private final Subject actor;
    private final Scope scope;
    private final Subject resource;
    private final String node;

    public RightsIdentity(Subject actor, Scope scope, Subject resource, String node) {
        Objects.requireNonNull(actor, "Actor cannot be null.");
        Objects.requireNonNull(scope, "Scope cannot be null.");
        Objects.requireNonNull(node, "Node cannot be null.");

        this.actor = actor;
        this.scope = scope;
        this.resource = resource;
        this.node = node;
    }

    public static @NotNull RightsIdentity of(Subject actor, Scope scope, @Nullable Subject resource, String node) {
        return new RightsIdentity(actor, scope, resource, node);
    }
}
