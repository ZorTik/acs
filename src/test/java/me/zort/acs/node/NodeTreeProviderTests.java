package me.zort.acs.node;

import me.zort.acs.scope.Scope;
import me.zort.acs.source.DefinitionsSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NodeTreeProviderTests {
    private NodeTreeProvider nodeTreeProvider;
    @Autowired
    private ObjectProvider<BlankNode> nodeObjectProvider;
    @Autowired
    private ObjectProvider<NodeTreeBuilder> nodeTreeBuilderProvider;

    private Scope testScope;
    private Scope testNullScope;

    @BeforeAll
    public void setup() {
        DefinitionsSource definitionsSource = mock(DefinitionsSource.class);

        testScope = mockScope("test");
        testNullScope = mockScope("testNull");

        when(definitionsSource.getNodeDelimiter()).thenReturn(":");
        when(definitionsSource.getScopes()).thenReturn(Set.of(testScope));
        when(definitionsSource.getNodes("test", "")).thenReturn(
                Set.of(nodeObjectProvider.getObject("test1"), nodeObjectProvider.getObject("test2")));
        when(definitionsSource.getNodes("test", "test1")).thenReturn(
                Set.of(nodeObjectProvider.getObject("test11")));
        when(definitionsSource.getNodes("test", "test1:test11")).thenReturn(
                Set.of(nodeObjectProvider.getObject("test111")));

        nodeTreeProvider = spy(new NodeTreeProvider(definitionsSource, nodeTreeBuilderProvider));
    }

    @Test
    public void shouldReturnNullTree() {
        assertThat(nodeTreeProvider.getTree(testNullScope)).isNull();
    }

    @Test
    public void shouldReturnRightTree() {
        INode tree = nodeTreeProvider.getTree(testScope);

        assertThat(tree).isNotNull();
        assertThat(tree.getValue()).isEqualTo("");
        assertThat(tree.getNodes()).hasSize(2);
        assertThat(tree.getNodes().iterator().next().getValue()).isEqualTo("test1");
        assertThat(tree.getNodes().iterator().next().getNodes()).hasSize(1);
        assertThat(tree.getNodes().iterator().next().getNodes().iterator().next().getValue()).isEqualTo("test11");
        assertThat(tree.getNodes().iterator().next().getNodes().iterator().next().getNodes()).hasSize(1);
        assertThat(tree.getNodes().iterator().next().getNodes().iterator().next().getNodes().iterator().next().getValue()).isEqualTo("test111");
    }

    private Scope mockScope(String id) {
        Scope scope = mock(Scope.class);
        when(scope.getId()).thenReturn(id);
        when(scope.isSubjectBased()).thenReturn(true);
        when(scope.getAllowedAccessors()).thenReturn(Set.of());
        return scope;
    }
}
