package me.zort.acs.node;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public abstract class AbstractNode implements INode {
    private final Set<INode> nodes;

    public AbstractNode() {
        this.nodes = new ConcurrentSkipListSet<>();
    }

    public abstract boolean equals(Object o);
    public abstract int hashCode();
    public abstract int compareTo(@NotNull INode o);

    @Override
    public void addNode(INode node) {
        nodes.add(node);
    }

    @Override
    public Set<INode> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }
}
