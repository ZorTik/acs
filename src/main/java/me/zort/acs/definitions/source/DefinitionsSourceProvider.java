package me.zort.acs.definitions.source;

import me.zort.acs.definitions.source.yaml.YamlDefinitionsSourceProvider;
import me.zort.acs.exception.DefinitionsSourceException;
import org.jetbrains.annotations.NotNull;

public interface DefinitionsSourceProvider {

    DefinitionsSource getDefinitionsSource();

    static @NotNull DefinitionsSourceProvider getProvider(String fullSchema) {
        String[] schemaSplit = fullSchema.split(":", 2);

        if (schemaSplit.length == 0) {
            throw new DefinitionsSourceException("Invalid source schema: " + fullSchema, null);
        }

        String format = schemaSplit[0];

        switch (format.toLowerCase()) {
            case "yaml":
                return new YamlDefinitionsSourceProvider(fullSchema.substring(format.length() + 1));
            default:
                throw new DefinitionsSourceException("Unknown format: " + format, null);
        }
    }
}
