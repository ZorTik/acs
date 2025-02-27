package me.zort.acs.http.entity;

import me.zort.acs.http.dto.ResponseWrapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseEntityWrapper<T> extends ResponseEntity<ResponseWrapper<T>> {
    public ResponseEntityWrapper(int status, String message, T body) {
        super(new ResponseWrapper<>(status, message, body), HttpStatusCode.valueOf(status));
    }

    public static @NotNull <T> ResponseEntityWrapper<T> okWrapper(T body) {
        return new ResponseEntityWrapper<>(200, "OK", body);
    }
}
