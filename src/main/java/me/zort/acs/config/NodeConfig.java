package me.zort.acs.config;

import me.zort.acs.node.BlankNode;
import me.zort.acs.node.Node;
import me.zort.acs.node.NodeDescription;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class NodeConfig {

    @SuppressWarnings("all")
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Node blankWithValue(String value, @Nullable NodeDescription description) {
        if (description == null) {
            return new BlankNode(value);
        } else {
            return new Node(value, description);
        }
    }
}
