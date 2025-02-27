package me.zort.acs.http.dto;

public interface GrantRequest {

    SubjectDto getFrom();
    SubjectDto getTo();
}
