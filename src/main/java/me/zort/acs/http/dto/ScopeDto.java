package me.zort.acs.http.dto;

import lombok.Getter;
import me.zort.acs.scope.Scope;

import java.util.List;

@Getter
public final class ScopeDto {
    private final String id;
    private final boolean subjectBased;
    private final List<ScopeDto> allowedAccessors;

    public ScopeDto(Scope scope) {
        this.id = scope.getId();
        this.subjectBased = scope.isSubjectBased();
        this.allowedAccessors = scope.getAllowedAccessors()
                .stream()
                .map(ScopeDto::new)
                .toList();
    }
}
