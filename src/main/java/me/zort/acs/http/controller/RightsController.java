package me.zort.acs.http.controller;

import lombok.RequiredArgsConstructor;
import me.zort.acs.exception.AccessGrantException;
import me.zort.acs.exception.RecordConflict;
import me.zort.acs.exception.ScopeNotFoundException;
import me.zort.acs.grant.AccessGrantStrategy;
import me.zort.acs.grant.Grant;
import me.zort.acs.http.dto.*;
import me.zort.acs.http.entity.ResponseEntityWrapper;
import me.zort.acs.subject.Subject;
import me.zort.acs.subject.SubjectConverter;
import me.zort.acs.util.Responses;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/rights")
@Controller
public class RightsController {
    private final AccessGrantStrategy accessGrantStrategy;
    private final SubjectConverter subjectConverter;

    @PostMapping("/check")
    public ResponseEntityWrapper<RightsCheckResponse> check(@RequestBody RightsCheckRequest request) {
        try {
            Map<String, Boolean> results = request.getNodes()
                    .stream()
                    .map(grantFactory(request))
                    .collect(Collectors.toMap(Grant::getNode, this::doCheck));

            return Responses.ok(new RightsCheckResponse(results));
        } catch (ScopeNotFoundException e) {
            return Responses.notFound("scope not found");
        }
    }

    @PostMapping("/grant")
    public ResponseEntityWrapper<RightsGrantResponse> grant(@RequestBody RightsGrantRequest request) {
        try {
            Map<String, Boolean> results = request.getNodes()
                    .stream()
                    .sorted(Comparator.comparingInt(String::length))
                    .sorted(Comparator.reverseOrder())
                    .map(grantFactory(request))
                    .collect(Collectors.toMap(Grant::getNode, this::doGrant));

            return Responses.ok(new RightsGrantResponse(results));
        } catch (ScopeNotFoundException e) {
            return Responses.notFound("scope not found");
        }
    }

    private Function<String, Grant> grantFactory(GrantRequest grantRequest) throws ScopeNotFoundException {
        Subject from = subjectConverter.convert(grantRequest.getFrom());
        Subject to = subjectConverter.convert(grantRequest.getTo());

        return node -> new Grant(from, to, node);
    }

    private boolean doCheck(Grant grant) {
        try {
            accessGrantStrategy.checkAccess(grant);
            return true;
        } catch (AccessGrantException e) {
            return false;
        }
    }

    private boolean doGrant(Grant grant) {
        try {
            accessGrantStrategy.grantAccess(grant);
            return true;
        } catch (AccessGrantException | RecordConflict e) {
            return false;
        }
    }
}
