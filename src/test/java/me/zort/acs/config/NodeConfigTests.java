package me.zort.acs.config;

import me.zort.acs.node.BlankNode;
import me.zort.acs.node.Node;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NodeConfigTests {
    @Autowired
    private ObjectProvider<Node> nodeObjectProvider;

    @Test
    public void nodeGeneratedIsNotNull() {
        assertThat(nodeObjectProvider.getObject("test", null)).isNotNull();
    }

    @Test
    public void nodeGeneratedShouldHaveExpectedValue() {
        assertThat(nodeObjectProvider.getObject("test", null).getValue()).isEqualTo("test");
    }
}
