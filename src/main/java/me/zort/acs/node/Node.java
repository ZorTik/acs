package me.zort.acs.node;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public class Node extends AbstractNode {
    private final String value;
    private final NodeDescription description;

    public Node(String value, NodeDescription description) {
        super();
        this.value = value;
        this.description = description;
    }

    @Override
    public int compareTo(@NotNull INode o) {
        return getValue().compareTo(o.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node node)) return false;
        return Objects.equals(getValue(), node.getValue()) && Objects.equals(getDescription(), node.getDescription()) && Objects.equals(getNodes(), node.getNodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getDescription(), getNodes());
    }
}
