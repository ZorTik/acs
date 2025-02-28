package me.zort.acs.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantResult {
    private boolean result;
    private String error;

    public static @NotNull GrantResult success() {
        return new GrantResult(true, null);
    }

    public static @NotNull GrantResult error(String error) {
        return new GrantResult(false, error);
    }
}
