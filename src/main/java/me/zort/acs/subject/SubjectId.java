package me.zort.acs.subject;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class SubjectId implements Serializable {
    private String id;
    private String scopeId;

    public SubjectId(String id, String scopeId) {
        Objects.requireNonNull(id, "Id cannot be null.");
        Objects.requireNonNull(scopeId, "ScopeId cannot be null.");

        this.id = id;
        this.scopeId = scopeId;
    }

    public static @NotNull SubjectId of(String id, String scopeId) {
        return new SubjectId(id, scopeId);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SubjectId subjectId)) return false;
        return Objects.equals(getId(), subjectId.getId()) && Objects.equals(getScopeId(), subjectId.getScopeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getScopeId());
    }
}
