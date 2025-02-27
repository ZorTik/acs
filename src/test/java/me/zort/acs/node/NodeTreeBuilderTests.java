package me.zort.acs.node;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NodeTreeBuilderTests {
    @Autowired
    private NodeTreeBuilder nodeTreeBuilder;
    @Autowired
    private ObjectProvider<BlankNode> nodeObjectProvider;

    @Test
    public void shouldHaveOnlyRootNode() {
        assertThat(nodeTreeBuilder.getTree()).isNotNull().isInstanceOf(BlankNode.class);
        assertThat(nodeTreeBuilder.getTree().getNodes()).isNotNull().isEmpty();
    }

    @Test
    public void shouldHaveOneChild() {
        nodeTreeBuilder.linkNode(new String[] {""}, nodeObjectProvider.getObject("child"));
        assertThat(nodeTreeBuilder.getTree().getNodes()).hasSize(1);
        assertThat(nodeTreeBuilder.getTree().getNodes().iterator().next().getValue()).isEqualTo("child");
    }

    @Test
    public void shouldHaveTwoChildren() {
        nodeTreeBuilder.linkNode(new String[] {""}, nodeObjectProvider.getObject("child1"));
        nodeTreeBuilder.linkNode(new String[] {""}, nodeObjectProvider.getObject("child2"));
        assertThat(nodeTreeBuilder.getTree().getNodes()).hasSize(2);

        Iterator<INode> iterator = nodeTreeBuilder.getTree().getNodes().iterator();

        assertThat(iterator.next().getValue()).isEqualTo("child1");
        assertThat(iterator.next().getValue()).isEqualTo("child2");
    }

    @Test
    public void shouldHaveTwoChildrenWithOneInner() {
        nodeTreeBuilder.linkNode(new String[] {""}, nodeObjectProvider.getObject("child1"));
        nodeTreeBuilder.linkNode(new String[] {""}, nodeObjectProvider.getObject("child2"));
        nodeTreeBuilder.linkNode(new String[] {"", "child2"}, nodeObjectProvider.getObject("child21"));
        assertThat(nodeTreeBuilder.getTree().getNodes()).hasSize(2);

        Iterator<INode> iterator = nodeTreeBuilder.getTree().getNodes().iterator();

        iterator.next();
        INode child2 = iterator.next();

        assertThat(child2.getValue()).isEqualTo("child2");
        assertThat(child2.getNodes()).hasSize(1);
        assertThat(child2.getNodes().iterator().next().getValue()).isEqualTo("child21");
    }
}
