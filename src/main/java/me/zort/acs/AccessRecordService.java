package me.zort.acs;

import me.zort.acs.exception.RecordConflict;
import me.zort.acs.exception.TransactionRollbackException;
import me.zort.acs.node.INode;
import me.zort.acs.rights.Rights;
import me.zort.acs.scope.Scope;
import me.zort.acs.subject.Subject;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.function.Supplier;

public interface AccessRecordService {

    void createMissingDefinitions(Map<Scope, INode> definitions);

    void createSubject(Scope scope, String id) throws RecordConflict;

    void deleteSubject(Scope scope, String id) throws RecordConflict;

    @Nullable Subject getSubject(Scope scope, String id);

    void createRights(Rights rights);

    void deleteRights(Rights rights);

    boolean hasRights(Rights rights);

    default void createSubject(Subject subject) throws RecordConflict {
        createSubject(subject.getScope(), subject.getId());
    }

    default boolean createSubjectIfNotExists(Subject subject) {
        try {
            createSubject(subject);
            return true;
        } catch (RecordConflict ignored) {
            // If not exists ignores this exception
            return false;
        }
    }

    @Transactional
    default void transactionallyCreateSubjectsAndPerform(Supplier<Boolean> task, Subject... subjects) {
        for (Subject subject : subjects) {
            createSubjectIfNotExists(subject);
        }

        if (!task.get()) {
            // Rollback
            throw new TransactionRollbackException();
        }
    }
}
