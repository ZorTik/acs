package me.zort.acs.exception;

import lombok.Getter;

@Getter
public class RecordConflict extends Exception {
    private final Object record;

    public RecordConflict(Object record) {
        super("Record already exists.");
        this.record = record;
    }
}
