package me.zort.acs.exception;

import lombok.Getter;
import me.zort.acs.source.DefinitionsSource;

@Getter
public class TreeConstructException extends RuntimeException {

    public TreeConstructException(String message) {
        super(message);
    }
}
