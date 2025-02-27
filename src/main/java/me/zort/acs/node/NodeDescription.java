package me.zort.acs.node;

import org.jetbrains.annotations.NotNull;

public record NodeDescription(String title, String subtitle) {

    public static @NotNull NodeDescription empty() {
        return new NodeDescription("", "");
    }
}
