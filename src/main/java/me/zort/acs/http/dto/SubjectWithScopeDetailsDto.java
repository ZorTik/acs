package me.zort.acs.http.dto;

import lombok.Getter;
import me.zort.acs.subject.Subject;

@Getter
public final class SubjectWithScopeDetailsDto {
    private final String id;
    private final ScopeDto scope;

    public SubjectWithScopeDetailsDto(Subject subject) {
        this.id = subject.getId();
        this.scope = new ScopeDto(subject.getScope());
    }
}
