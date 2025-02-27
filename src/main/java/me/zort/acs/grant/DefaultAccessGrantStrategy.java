package me.zort.acs.grant;

import me.zort.acs.AccessRecordService;
import me.zort.acs.exception.AccessGrantException;
import me.zort.acs.exception.NodeNotFoundException;
import me.zort.acs.exception.RecordConflict;
import me.zort.acs.node.NodeTreeHolder;
import me.zort.acs.rights.Rights;
import me.zort.acs.rights.RightsIdentity;
import me.zort.acs.scope.Scoped;
import me.zort.acs.definitions.source.DefinitionsSource;
import me.zort.acs.subject.Subject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DefaultAccessGrantStrategy implements AccessGrantStrategy {
    private final NodeTreeHolder nodeTreeHolder;
    private final AccessRecordService accessRecordService;
    private final DefinitionsSource definitionsSource;

    @Autowired
    public DefaultAccessGrantStrategy(
            @Qualifier("AccessDefinitionsService") NodeTreeHolder nodeTreeHolder,
            AccessRecordService accessRecordService, DefinitionsSource definitionsSource) {
        this.nodeTreeHolder = nodeTreeHolder;
        this.accessRecordService = accessRecordService;
        this.definitionsSource = definitionsSource;
    }

    @Override
    public void grantAccess(Grant grant) throws AccessGrantException, RecordConflict {
        accessRecordService.createRights(rightsForGrant(grant));
    }

    @Override
    public void revokeAccess(Grant grant) throws AccessGrantException, RecordConflict {
        accessRecordService.deleteRights(rightsForGrant(grant));
    }

    @Override
    public boolean checkAccess(Grant grant) throws AccessGrantException {
        return accessRecordService.hasRights(rightsForGrant(grant));
    }

    private @NotNull Rights rightsForGrant(Grant grant) throws AccessGrantException {
        Scoped target = grant.getTarget();

        Subject subject = null;
        if (target instanceof Subject targetSubject) {
            subject = targetSubject;
        }

        Rights rights = RightsIdentity.of(grant.getActor(), target.getScope(), subject, grant.getNode());

        ensureAccess(rights, target);

        return rights;
    }

    private void ensureAccess(Rights rights, Scoped target) throws AccessGrantException {
        Objects.requireNonNull(rights.getActor(), "Actor cannot be null");
        Objects.requireNonNull(rights.getActor().getId(), "Actor must be a subject with id");

        // Ensure that the target subject allows this subject to access its resources
        rights.getActor().ensureAccess(target, rights.getResource() != null);

        String node = rights.getNode();

        if (nodeTreeHolder.getNode(rights.getScope(), node.split(definitionsSource.getNodeDelimiter())) == null) {
            throw new NodeNotFoundException(node);
        }
    }
}
