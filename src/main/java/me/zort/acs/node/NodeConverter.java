package me.zort.acs.node;

import lombok.RequiredArgsConstructor;
import me.zort.acs.definitions.source.DefinitionsSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class NodeConverter {
    private final ObjectProvider<Node> nodeProvider;
    private final DefinitionsSource definitionsSource;

    /**
     * Converts definition to full node instance.
     * Reduction strategy is also applied. Suppose we have separator ":":
     * a:b:c -> c, d:e -> e, g -> g
     * This way, the node's name is reduced to only the last part of the
     * path.
     *
     * @param nodeDefinition The definition
     * @return The converted node
     */
    public @NotNull INode convert(INodeDefinition nodeDefinition) {
        String value = nodeDefinition.getValue();
        String delim = definitionsSource.getNodeDelimiter();

        return nodeProvider.getObject(
                value.substring(value.lastIndexOf(delim) + 1), nodeDefinition.getDescription());
    }
}
