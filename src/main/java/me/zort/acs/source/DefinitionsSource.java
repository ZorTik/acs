package me.zort.acs.source;

import me.zort.acs.node.INode;
import me.zort.acs.scope.Scope;

import java.util.Set;

public interface DefinitionsSource {

    Set<INode> getNodes(String scopeId, String prefix);

    Set<Scope> getScopes();

    default String getNodeDelimiter() {
        return ":";
    }
}