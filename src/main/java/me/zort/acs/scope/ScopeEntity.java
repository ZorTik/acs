package me.zort.acs.scope;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ScopeEntity implements Scope {
    @Id
    private String id;

    private boolean subjectBased;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "scope_allowed_accessors",
            joinColumns = @JoinColumn(name = "scope_allowed"),
            inverseJoinColumns = @JoinColumn(name = "scope_allowed_by")
    )
    @ToString.Exclude
    private Set<ScopeEntity> allowedAccessors = new HashSet<>();


    public static @NotNull ScopeEntity from(Scope scope) {
        return new ScopeEntity(scope.getId(), scope.isSubjectBased(), scope.getAllowedAccessors()
                .stream()
                .map(ScopeEntity::from)
                .collect(Collectors.toSet()));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ScopeEntity that = (ScopeEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
