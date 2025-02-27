package me.zort.acs.definitions.source.yaml;

import lombok.RequiredArgsConstructor;
import me.zort.acs.definitions.source.DefinitionsSource;
import me.zort.acs.definitions.source.DefinitionsSourceProvider;
import me.zort.acs.exception.DefinitionsSourceException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RequiredArgsConstructor
public class YamlDefinitionsSourceProvider implements DefinitionsSourceProvider {
    private final String schema;

    @Override
    public DefinitionsSource getDefinitionsSource() throws DefinitionsSourceException {
        String[] splitSchema = schema.split(":", 2);

        if (splitSchema.length != 2) {
            throw new DefinitionsSourceException("Invalid schema: " + schema);
        }

        InputStream in;
        if (splitSchema[0].equalsIgnoreCase("file")) {
            try {
                in = new FileInputStream(splitSchema[1]);
            } catch (FileNotFoundException e) {
                throw new DefinitionsSourceException("File not found: " + splitSchema[1], null, e);
            }
        } else {
            throw new DefinitionsSourceException("Invalid resource type: " + splitSchema[0]);
        }

        return new YamlDefinitionsSource(in);
    }
}
