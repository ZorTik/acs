package me.zort.acs.node;

import org.jetbrains.annotations.Nullable;

public interface INodeDefinition {

    String getValue();

    @Nullable NodeDescription getDescription();
}
