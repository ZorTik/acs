package me.zort.acs.definitions.source.yaml;

import me.zort.acs.definitions.source.DefinitionsSource;
import me.zort.acs.exception.DefinitionsSourceException;
import me.zort.acs.node.INodeDefinition;
import me.zort.acs.scope.Scope;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class YamlDefinitionsSource implements DefinitionsSource {
    private static final Yaml YAML = new Yaml(new Constructor(YamlDefinitionsModel.class, new LoaderOptions()));

    private final YamlDefinitionsModel model;

    public YamlDefinitionsSource(InputStream inputStream) {
        this.model = YAML.load(inputStream);

        mapAllowedScopes();
    }

    private void mapAllowedScopes() {
        for (YamlDefinitionsModel.ScopeDefinitionModel scopeModel : model.getScopes()) {
            if (scopeModel.getAllowedAccessorScopes().contains(scopeModel.getId())) {
                // Prevents stack overflow
                throw new DefinitionsSourceException(
                        String.format("Scope '%s' can't allow itself, it's allowed by default.", scopeModel.getId()));
            }

            scopeModel.setAllowedAccessors(scopeModel.getAllowedAccessorScopes()
                    .stream()
                    .map(this::getScope)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()));
        }
    }

    @Override
    public Set<? extends INodeDefinition> getNodes(String scopeId, String prefix) {
        return model.getScopes()
                .stream()
                .filter(it -> it.getId().equals(scopeId))
                .findFirst()
                .map(YamlDefinitionsModel.ScopeDefinitionModel::getNodes)
                .map(nodes -> nodes
                        .stream()
                        .filter(it -> it.getValue().startsWith(prefix))
                        .collect(Collectors.toUnmodifiableSet()))
                .orElse(Collections.emptySet());
    }

    @Override
    public Set<? extends Scope> getScopes() {
        return model.getScopes();
    }

    public Scope getScope(String scopeId) {
        return model.getScopes()
                .stream()
                .filter(scopeModel -> scopeModel.getId().equals(scopeId))
                .findFirst().orElse(null);
    }
}
