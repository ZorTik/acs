package me.zort.acs;

import lombok.RequiredArgsConstructor;
import me.zort.acs.exception.RecordConflict;
import me.zort.acs.node.INode;
import me.zort.acs.rights.Rights;
import me.zort.acs.rights.RightsEntity;
import me.zort.acs.rights.RightsEntityRepository;
import me.zort.acs.rights.RightsId;
import me.zort.acs.scope.Scope;
import me.zort.acs.scope.ScopeEntity;
import me.zort.acs.scope.ScopeEntityRepository;
import me.zort.acs.subject.Subject;
import me.zort.acs.subject.SubjectEntity;
import me.zort.acs.subject.SubjectEntityRepository;
import me.zort.acs.subject.SubjectId;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefaultAccessRecordService implements AccessRecordService {
    private final ScopeEntityRepository scopeEntityRepository;
    private final RightsEntityRepository rightsEntityRepository;
    private final SubjectEntityRepository subjectEntityRepository;

    @Override
    public void createMissingDefinitions(Map<Scope, INode> definitions) {
        scopeEntityRepository.saveAll(definitions.keySet()
                .stream()
                .map(ScopeEntity::from)
                .toList());
    }

    @Override
    public void createSubject(Scope scope, String id) throws RecordConflict {
        SubjectId subjectId = SubjectId.of(scope.getId(), id);

        if (subjectEntityRepository.existsById(subjectId)) {
            throw new RecordConflict(subjectId);
        }

        subjectEntityRepository.save(new SubjectEntity(subjectId));
    }

    @Override
    public void deleteSubject(Scope scope, String id) throws RecordConflict {
        SubjectId subjectId = SubjectId.of(scope.getId(), id);

        if (!subjectEntityRepository.existsById(subjectId)) {
            throw new RecordConflict(subjectId);
        }

        rightsEntityRepository.deleteAllByActorIdOrResourceId(subjectId.getId(), subjectId.getId());
        subjectEntityRepository.deleteById(subjectId);
    }

    @Override
    public @Nullable Subject getSubject(Scope scope, String id) {
        return subjectEntityRepository
                .findById(SubjectId.of(scope.getId(), id))
                .orElse(null);
    }

    @Override
    public void createRights(Rights rights) {
        rightsEntityRepository.deleteByActorIdAndScopeIdAndResourceIdAndNodeIsPrefixOf(
                rights.getActor().getId(),
                rights.getScope().getId(),
                rights.getResource() != null ? rights.getResource().getId() : null,
                rights.getNode());

        RightsEntity rightsEntity = new RightsEntity();
        rightsEntity.setActorId(rights.getActor().getId());
        rightsEntity.setScopeId(rights.getScope().getId());
        rightsEntity.setResourceId(rights.getResource() != null ? rights.getResource().getId() : null);
        rightsEntity.setNode(rights.getNode());
        rightsEntityRepository.save(rightsEntity);

        deleteSubRights(rights);
    }

    @Override
    public void deleteRights(Rights rights) {
        rightsEntityRepository.deleteById(new RightsId(rights));

        deleteSubRights(rights);
    }

    @Override
    public boolean hasRights(Rights rights) {
        return rightsEntityRepository.existsByActorIdAndScopeIdAndResourceIdAndNodeIsPrefixOf(
                rights.getActor().getId(),
                rights.getScope().getId(),
                rights.getResource() != null ? rights.getResource().getId() : null,
                rights.getNode());
    }

    private void deleteSubRights(Rights rights) {
        rightsEntityRepository.deleteAllWhereNodeStartsWith(
                rights.getActor().getId(),
                rights.getScope().getId(),
                rights.getResource() != null ? rights.getResource().getId() : null,
                rights.getNode());
    }
}
