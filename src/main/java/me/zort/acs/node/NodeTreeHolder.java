package me.zort.acs.node;

import me.zort.acs.scope.Scope;

import java.util.Objects;

public interface NodeTreeHolder {

    INode getTree(Scope scope);

    default INode getNode(Scope scope, String[] path) {
        INode tree = getTree(scope);

        if (tree == null) {
            return null;
        }

        return tree.getNodes()
                .stream()
                .map(node -> node.traverseFind(path))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }
}
