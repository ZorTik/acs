package me.zort.acs.definitions.source.yaml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zort.acs.node.INodeDefinition;
import me.zort.acs.node.NodeDescription;
import me.zort.acs.scope.Scope;

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
        // TODO: Na toto udělat parser, aby to bralo reference ze stejného souboru
        private Set<? extends Scope> allowedAccessors;
        private Set<NodeDefinitionModel> nodes;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class NodeDefinitionModel implements INodeDefinition {
        private String value;
        private NodeDescription description;

    }

}
