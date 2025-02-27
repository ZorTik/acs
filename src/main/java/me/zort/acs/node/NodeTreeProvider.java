package me.zort.acs.node;

import lombok.RequiredArgsConstructor;
import me.zort.acs.definitions.source.DefinitionsSource;
import me.zort.acs.scope.Scope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service("NodeTreeProvider")
public class NodeTreeProvider implements NodeTreeHolder {
    private final DefinitionsSource definitionsSource;
    private final ObjectProvider<NodeTreeBuilder> nodeTreeBuilderProvider;
    private final NodeConverter nodeConverter;

    public @Nullable INode getTree(@NotNull Scope scope) {
        Objects.requireNonNull(scope, "Scope cannot be null");

        NodeTreeBuilder nodeTreeBuilder = nodeTreeBuilderProvider.getObject();

        buildSubtree(nodeTreeBuilder, null, scope.getId(), "");

        if (nodeTreeBuilder.getTree().size() == 1) {
            // Tree is empty, so there were no nodes found for the scope
            // NodeTreeBuilder by default has one empty node
            return null;
        }

        return nodeTreeBuilder.getTree();
    }

    private void buildSubtree(NodeTreeBuilder builder, @Nullable INode node, String subjectId, String prefix) {
        String delim = definitionsSource.getNodeDelimiter();

        if (node != null) {
            String[] parentPath = prefix.split(delim);

            parentPath = Arrays.copyOfRange(parentPath, 0, parentPath.length - 1);

            builder.linkNode(parentPath, node);
        }

        Set<? extends INodeDefinition> nodes = definitionsSource.getNodes(subjectId, (prefix.length() > 1
                ? prefix.substring(1)
                : prefix) + (prefix.isEmpty() ? "" : delim));

        if (nodes == null) {
            return;
        }

        nodes = nodes.stream()
                .sorted(Comparator.comparingInt(def -> def.getValue().length()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (INodeDefinition node1Def : nodes) {
            INode node1 = nodeConverter.convert(node1Def);

            buildSubtree(builder, node1, subjectId, delim + node1Def.getValue());
        }
    }
}
