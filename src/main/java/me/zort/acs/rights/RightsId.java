package me.zort.acs.rights;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RightsId implements Serializable {
    private String actorId;
    private String scopeId;
    private String resourceId;
    private String node;

    public RightsId(Rights rights) {
        this.actorId = rights.getActor().getId();
        this.scopeId = rights.getScope().getId();
        this.resourceId = rights.getResource() != null ? rights.getResource().getId() : null;
        this.node = rights.getNode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RightsId rightsId = (RightsId) o;
        return Objects.equals(actorId, rightsId.actorId) &&
                Objects.equals(scopeId, rightsId.scopeId) &&
                Objects.equals(resourceId, rightsId.resourceId) &&
                Objects.equals(node, rightsId.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, scopeId, resourceId, node);
    }
}
