package me.zort.acs.http.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseWrapper<T> {
    private final int status;
    private final String message;
    private final T data;

}
