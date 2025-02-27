package me.zort.acs.exception;

import lombok.Getter;
import me.zort.acs.definitions.source.DefinitionsSource;
import org.jetbrains.annotations.Nullable;

@Getter
public class DefinitionsSourceException extends RuntimeException {
    private final DefinitionsSource definitionsSource;

    public DefinitionsSourceException(String message) {
        super(message);
        this.definitionsSource = null;
    }

    public DefinitionsSourceException(String message, @Nullable DefinitionsSource definitionsSource) {
        super(message);
        this.definitionsSource = definitionsSource;
    }

    public DefinitionsSourceException(String message, @Nullable DefinitionsSource definitionsSource, Throwable cause) {
        super(message, cause);
        this.definitionsSource = definitionsSource;
    }
}
