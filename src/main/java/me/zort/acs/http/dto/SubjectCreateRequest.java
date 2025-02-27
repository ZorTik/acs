package me.zort.acs.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubjectCreateRequest {
    private String id;
    private String scope;

    // TODO: Validační anotace

}
