package me.zort.acs.subject;

import lombok.RequiredArgsConstructor;
import me.zort.acs.definitions.DefinitionsService;
import me.zort.acs.exception.ScopeNotFoundException;
import me.zort.acs.http.dto.SubjectDto;
import me.zort.acs.scope.Scope;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectConverter {
    private final DefinitionsService definitionsService;
    private final ObjectProvider<Subject> subjectProvider;

    public @NotNull Subject convert(SubjectDto subjectDto) throws ScopeNotFoundException {
        String scopeName = subjectDto.getScope();
        Scope scope = definitionsService.getScope(scopeName);

        if (scope == null) {
            throw new ScopeNotFoundException(scopeName);
        }

        return subjectProvider.getObject(subjectDto.getId(), scope);
    }
}
