package me.zort.acs.http.dto;

import lombok.Getter;
import me.zort.acs.subject.Subject;

@Getter
public final class SubjectDto {
    private final String id;
    private final ScopeDto scope;

    public SubjectDto(Subject subject) {
        this.id = subject.getId();
        this.scope = new ScopeDto(subject.getScope());
    }
}
