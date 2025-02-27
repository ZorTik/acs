package me.zort.acs.http.entity;

import me.zort.acs.http.dto.ResponseWrapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseEntityWrapper<T> extends ResponseEntity<ResponseWrapper<T>> {
    public ResponseEntityWrapper(int status, String message, T body) {
        super(new ResponseWrapper<>(status, message, body), HttpStatusCode.valueOf(status));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public @NotNull String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
