package me.zort.acs.config;

import me.zort.acs.definitions.source.DefinitionsSourceProvider;
import me.zort.acs.node.INode;
import me.zort.acs.scope.Scope;
import me.zort.acs.definitions.source.DefinitionsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AccessSourceConfig {
    private final String sourceSchema;

    @Autowired
    public AccessSourceConfig(@Value("${definitions.source}") String sourceSchema) {
        this.sourceSchema = sourceSchema;
    }

    @Bean
    public DefinitionsSource accessConfigSource() {
        return DefinitionsSourceProvider.getProvider(sourceSchema).getDefinitionsSource();
    }
}
