package me.zort.acs.subject;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.scope.ScopeEntity;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@IdClass(SubjectId.class)
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEntity implements Subject {
    @Id
    private String id;

    @Id
    @Column(nullable = false)
    private String scopeId;

    @ManyToOne()
    @JoinColumn(name = "scopeId", referencedColumnName = "id", insertable = false, updatable = false)
    private ScopeEntity scope;

    public SubjectEntity(SubjectId subjectId) {
        setId(subjectId);
    }

    public void setId(SubjectId subjectId) {
        this.id = subjectId.getId();
        this.scopeId = subjectId.getScopeId();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SubjectEntity that = (SubjectEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId())
                && getScopeId() != null && Objects.equals(getScopeId(), that.getScopeId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, scopeId);
    }
}
