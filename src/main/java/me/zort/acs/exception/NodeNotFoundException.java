package me.zort.acs.exception;

import lombok.Getter;

@Getter
public class NodeNotFoundException extends AccessGrantException {
    private final String fullValue;

    public NodeNotFoundException(String fullValue) {
        super(String.format("Node %s not found.", fullValue));
        this.fullValue = fullValue;
    }
}
