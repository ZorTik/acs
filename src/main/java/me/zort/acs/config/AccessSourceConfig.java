package me.zort.acs.config;

import me.zort.acs.node.INode;
import me.zort.acs.scope.Scope;
import me.zort.acs.source.DefinitionsSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AccessSourceConfig {

    @Bean
    public DefinitionsSource accessConfigSource() {
        // TODO

        return new DefinitionsSource() {
            @Override
            public Set<INode> getNodes(String scopeId, String prefix) {
                return Set.of();
            }

            @Override
            public Set<Scope> getScopes() {
                return Set.of();
            }
        };
    }
}
