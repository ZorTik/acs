package me.zort.acs.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NodeDescription {
    private String title;
    private String subtitle;


    public static @NotNull NodeDescription empty() {
        return new NodeDescription("", "");
    }
}
