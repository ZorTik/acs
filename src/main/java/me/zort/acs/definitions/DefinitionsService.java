package me.zort.acs.definitions;

import me.zort.acs.AccessRecordService;
import me.zort.acs.definitions.source.DefinitionsSource;
import me.zort.acs.node.INode;
import me.zort.acs.node.NodeTreeHolder;
import me.zort.acs.scope.Scope;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("AccessDefinitionsService")
public class DefinitionsService implements NodeTreeHolder {
    private final DefinitionsSource definitionsSource;
    private final NodeTreeHolder nodeTreeProvider;
    private final AccessRecordService accessRecordService;

    private Map<Scope, INode> definitions;

    @Autowired
    public DefinitionsService(
            DefinitionsSource definitionsSource,
            @Qualifier("NodeTreeProvider") NodeTreeHolder nodeTreeProvider,
            AccessRecordService accessRecordService) {
        this.definitionsSource = definitionsSource;
        this.nodeTreeProvider = nodeTreeProvider;
        this.accessRecordService = accessRecordService;
    }

    public void refresh() {
        this.definitions = definitionsSource.getScopes()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(), nodeTreeProvider::getTree, (a, b) -> a,
                        ConcurrentHashMap::new));
        accessRecordService.createMissingDefinitions(definitions);
    }

    @Override
    public INode getTree(Scope scope) {
        return definitions.get(scope);
    }


    public @Nullable Scope getScope(String id) {
        return definitions.keySet()
                .stream()
                .filter(scope -> scope.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Set<Scope> getScopes() {
        return definitions.keySet();
    }
}
