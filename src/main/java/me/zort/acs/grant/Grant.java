package me.zort.acs.grant;

import lombok.Getter;
import me.zort.acs.scope.Scoped;
import me.zort.acs.subject.Subject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public class Grant {
    private final Subject actor;
    private final Scoped target;
    private final String node;

    public Grant(Subject actor, Scoped target, String node) {
        Objects.requireNonNull(actor, "Actor cannot be null.");
        Objects.requireNonNull(target, "Target cannot be null.");
        Objects.requireNonNull(node, "Node cannot be null.");

        this.actor = actor;
        this.target = target;
        this.node = node;
    }

    public static @NotNull Grant of(Subject actor, Scoped target, String node) {
        return new Grant(actor, target, node);
    }
}
