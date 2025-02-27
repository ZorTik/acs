package me.zort.acs.node;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NodeDefinition {
    private final String value;
    private final NodeDescription description;

}
