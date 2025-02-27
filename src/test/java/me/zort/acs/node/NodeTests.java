package me.zort.acs.node;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NodeTests {
    @Autowired
    private ObjectProvider<BlankNode> nodeObjectProvider;

    @Test
    public void shouldFindRoot() {
        INode nodeFound = nodeObjectProvider.getObject("").traverseFind(new String[]{""});

        assertThat(nodeFound).isNotNull();
        assertThat(nodeFound.getValue()).isEqualTo("");
    }

    @Test
    public void shouldFindChildren() {
        BlankNode root = nodeObjectProvider.getObject("");
        BlankNode child = nodeObjectProvider.getObject("child");
        root.addNode(child);

        INode found = root.traverseFind(new String[]{"", "child"});

        assertThat(found).isNotNull();
        assertThat(found.getValue()).isEqualTo("child");
        assertThat(root.traverseFind(new String[]{"","child.child1"})).isNull();

        child.addNode(nodeObjectProvider.getObject("child1"));

        assertThat(root.traverseFind(new String[]{"","child", "child1"})).isNotNull();
        assertThat(root.traverseFind(new String[]{"","child", "child1"}).getValue()).isEqualTo("child1");

        assertThat(root.traverseFind(new String[]{"child1"})).isNull();
        assertThat(root.traverseFind(new String[]{"","child1"})).isNull();
    }

    @Test
    public void shouldReturnRightSize() {
        BlankNode root = nodeObjectProvider.getObject("");
        BlankNode child = nodeObjectProvider.getObject("child");
        BlankNode child2 = nodeObjectProvider.getObject("child2");

        assertThat(root.size()).isEqualTo(1);

        root.addNode(child);

        assertThat(root.size()).isEqualTo(2);

        child.addNode(child2);

        assertThat(root.size()).isEqualTo(3);
    }
}
