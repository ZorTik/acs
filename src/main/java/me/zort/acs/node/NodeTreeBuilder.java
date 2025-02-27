package me.zort.acs.node;

import lombok.Getter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class NodeTreeBuilder {
    private final ObjectProvider<Node> nodeObjectProvider;
    @Getter
    private final INode tree;

    @Autowired
    public NodeTreeBuilder(ObjectProvider<Node> nodeObjectProvider) {
        this.nodeObjectProvider = nodeObjectProvider;
        this.tree = nodeObjectProvider.getObject("", null);
    }

    public void linkNode(String[] parentPath, INode node) {
        tree.traverseFind(parentPath, nodeObjectProvider).addNode(node);
    }
}
