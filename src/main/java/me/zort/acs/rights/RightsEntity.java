package me.zort.acs.rights;

import jakarta.persistence.*;
import lombok.*;
import me.zort.acs.scope.ScopeEntity;
import me.zort.acs.subject.SubjectEntity;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@IdClass(RightsId.class)
@Entity
public class RightsEntity {
    @Id
    @Column(nullable = false)
    private String actorId;

    @Id
    @Column(nullable = false)
    private String scopeId;

    @Id
    @Column
    private String resourceId;

    @Id
    @Column(nullable = false)
    private String node;

    @ManyToOne
    @JoinColumn(name = "actorId", referencedColumnName = "id", insertable = false, updatable = false)
    private SubjectEntity actor;

    @ManyToOne
    @JoinColumn(name = "scopeId", referencedColumnName = "id", insertable = false, updatable = false)
    private ScopeEntity scope;

    @ManyToOne
    @JoinColumn(name = "resourceId", referencedColumnName = "id", insertable = false, updatable = false)
    private SubjectEntity resource;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RightsEntity that = (RightsEntity) o;
        return getActorId() != null && Objects.equals(getActorId(), that.getActorId())
                && getScopeId() != null && Objects.equals(getScopeId(), that.getScopeId())
                && getResourceId() != null && Objects.equals(getResourceId(), that.getResourceId())
                && getNode() != null && Objects.equals(getNode(), that.getNode());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(actorId,
                scopeId,
                resourceId,
                node);
    }
}
