package me.zort.acs;

import me.zort.acs.exception.RecordConflict;
import me.zort.acs.node.INode;
import me.zort.acs.rights.Rights;
import me.zort.acs.scope.Scope;
import me.zort.acs.subject.Subject;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface AccessRecordService {

    void createMissingDefinitions(Map<Scope, INode> definitions);

    void createSubject(Scope scope, String id) throws RecordConflict;

    void deleteSubject(Scope scope, String id) throws RecordConflict;

    @Nullable Subject getSubject(Scope scope, String id);

    void createRights(Rights rights);

    void deleteRights(Rights rights);

    boolean hasRights(Rights rights);
}
