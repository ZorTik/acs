package me.zort.acs.definitions.source;

import me.zort.acs.node.INodeDefinition;
import me.zort.acs.scope.Scope;

import java.util.Set;

public interface DefinitionsSource {

    Set<? extends INodeDefinition> getNodes(String scopeId, String prefix);

    Set<? extends Scope> getScopes();

    default String getNodeDelimiter() {
        return ":";
    }
}