package me.zort.acs.exception;

import lombok.Getter;

@Getter
public class TreeConstructException extends RuntimeException {

    public TreeConstructException(String message) {
        super(message);
    }
}
