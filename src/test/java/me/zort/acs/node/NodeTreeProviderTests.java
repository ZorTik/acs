package me.zort.acs.node;

import me.zort.acs.scope.Scope;
import me.zort.acs.definitions.source.DefinitionsSource;
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
    private DefinitionsSource definitionsSource;
    @Autowired
    private ObjectProvider<NodeTreeBuilder> nodeTreeBuilderProvider;
    @Autowired
    private NodeConverter nodeConverter;

    private Scope testScope;
    private Scope testNullScope;

    @BeforeAll
    public void setup() {
        testScope = definitionsSource.getScopes().iterator().next();
        testNullScope = mock(Scope.class);
        when(testNullScope.getId()).thenReturn("testNull");
        when(testNullScope.isSubjectBased()).thenReturn(true);
        when(testNullScope.getAllowedAccessors()).thenReturn(Set.of());

        nodeTreeProvider = spy(new NodeTreeProvider(definitionsSource, nodeTreeBuilderProvider, nodeConverter));
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
}
