package me.zort.acs.definitions.source.yaml;

import lombok.*;
import me.zort.acs.node.INodeDefinition;
import me.zort.acs.node.NodeDescription;
import me.zort.acs.scope.Scope;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class YamlDefinitionsModel {
    private Set<ScopeDefinitionModel> scopes;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ScopeDefinitionModel implements Scope {
        private String id;
        private boolean subjectBased;
        private Set<String> allowedAccessorScopes;
        private Set<NodeDefinitionModel> nodes;

        private transient Set<? extends Scope> allowedAccessors;

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            ScopeDefinitionModel that = (ScopeDefinitionModel) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class NodeDefinitionModel implements INodeDefinition {
        private String value;
        private NodeDescription description;

    }

}
