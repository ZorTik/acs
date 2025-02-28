package me.zort.acs.http.dto;

import me.zort.acs.exception.ScopeNotFoundException;
import me.zort.acs.grant.Grant;
import me.zort.acs.subject.Subject;
import me.zort.acs.subject.SubjectConverter;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Stream;

public interface GrantRequest {

    SubjectDto getFrom();
    SubjectDto getTo();
    Set<String> getNodes();

    default Stream<Grant> grantStream(SubjectConverter subjectConverter) throws ScopeNotFoundException {
        Subject from = subjectConverter.convert(getFrom());
        Subject to = subjectConverter.convert(getTo());

        return getNodes()
                .stream()
                .sorted(Comparator.comparingInt(String::length))
                .sorted(Comparator.reverseOrder())
                .map(node -> new Grant(from, to, node));
    }
}
