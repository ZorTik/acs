package me.zort.acs.node;

import me.zort.acs.scope.Scope;

public interface NodeTreeHolder {

    INode getTree(Scope scope);

    default INode getNode(Scope scope, String[] path) {
        INode tree = getTree(scope);

        if (tree == null) {
            return null;
        }

        return tree.traverseFind(path);
    }
}
