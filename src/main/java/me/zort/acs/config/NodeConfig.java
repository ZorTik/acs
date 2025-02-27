package me.zort.acs.config;

import me.zort.acs.node.BlankNode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class NodeConfig {

    @SuppressWarnings("all")
    @Bean("BlankNode")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public BlankNode blankWithValue(String value) {
        return new BlankNode(value);
    }
}
