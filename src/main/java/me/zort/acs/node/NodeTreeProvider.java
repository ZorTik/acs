package me.zort.acs.node;

import lombok.RequiredArgsConstructor;
import me.zort.acs.source.DefinitionsSource;
import me.zort.acs.scope.Scope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service("NodeTreeProvider")
public class NodeTreeProvider implements NodeTreeHolder {
    private final DefinitionsSource definitionsSource;
    private final ObjectProvider<NodeTreeBuilder> nodeTreeBuilderProvider;

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

        Set<INode> nodes = definitionsSource.getNodes(subjectId, prefix.length() > 1
                ? prefix.substring(1)
                : prefix);

        if (nodes == null) {
            return;
        }

        for (INode node1 : nodes) {
            String subValue = prefix + delim + node1.getValue();

            buildSubtree(builder, node1, subjectId, subValue);
        }
    }
}
