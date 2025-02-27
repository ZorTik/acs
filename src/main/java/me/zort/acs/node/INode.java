package me.zort.acs.node;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface INode extends Comparable<INode> {
    String getValue();

    @Nullable NodeDescription getDescription();

    void addNode(INode node);

    Set<INode> getNodes();

    default int size() {
        return getNodes()
                .stream()
                .mapToInt(INode::size)
                .sum() + 1;
    }

    default Set<String> joinNodeValues(String delim) {
        Set<String> values = new HashSet<>();
        values.add(getValue());

        for (INode node : getNodes()) {
            node.joinNodeValues(delim).forEach(value -> values.add(getValue() + delim + value));
        }
        return values;
    }

    default @Nullable INode traverseFind(String[] path) {
        return traverseFind(path, null);
    }

    default @Nullable INode traverseFind(String[] path, @Nullable ObjectProvider<? extends INode> factoryIfCreate) {
        if (path.length == 0) {
            // Reached end of tree
            return null;
        }
        if (path.length == 1 && path[0].equals(getValue())) {
            // The path points to this node
            return this;
        } else if(path.length == 1) {
            // The path does not even start with the right value
            return null;
        }

        String next = path[1];

        for (INode node : getNodes()) {
            if (node.getValue().equals(next)) {
                path = Arrays.copyOfRange(path, 1, path.length);

                return node.traverseFind(path, factoryIfCreate);
            }
        }

        if (factoryIfCreate == null) {
            return null;
        } else {
            INode node = factoryIfCreate.getObject(next);
            addNode(node);

            return traverseFind(path, factoryIfCreate);
        }
    }
}
