package me.zort.acs.node;

import java.util.Objects;

public class BlankNode extends Node {

    public BlankNode(String value) {
        super(Objects.requireNonNull(value), NodeDescription.empty());
    }
}
