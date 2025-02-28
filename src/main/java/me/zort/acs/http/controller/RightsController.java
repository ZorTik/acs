package me.zort.acs.http.controller;

import lombok.RequiredArgsConstructor;
import me.zort.acs.AccessRecordService;
import me.zort.acs.exception.AccessGrantException;
import me.zort.acs.exception.RecordConflict;
import me.zort.acs.exception.ScopeNotFoundException;
import me.zort.acs.exception.TransactionRollbackException;
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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/rights")
@Controller
public class RightsController {
    private final AccessGrantStrategy accessGrantStrategy;
    private final AccessRecordService accessRecordService;
    private final SubjectConverter subjectConverter;

    // TODO: Udělat, že při /rights/revoke se smažou subjekty pokud už nejsou s nimi spojené žádné rights v databázi

    @PostMapping("/check")
    public ResponseEntityWrapper<RightsCheckResponse> check(@RequestBody RightsCheckRequest request) {
        try {
            Map<String, Boolean> results = request
                    .grantStream(subjectConverter)
                    .collect(Collectors.toMap(Grant::getNode, this::doCheck));

            return Responses.ok(new RightsCheckResponse(results));
        } catch (ScopeNotFoundException e) {
            return Responses.notFound("scope not found");
        }
    }

    @PostMapping("/grant")
    public ResponseEntityWrapper<RightsGrantResponse> grant(@RequestBody RightsGrantRequest request) {
        try {
            Stream<Grant> grantStream = request.grantStream(subjectConverter);

            Map<String, Boolean> results = new HashMap<>();

            accessRecordService.transactionallyCreateSubjectsAndPerform(() -> {
                grantStream
                        .forEach(grant -> {
                            boolean result;
                            try {
                                accessGrantStrategy.grantAccess(grant);
                                result = true;
                            } catch (AccessGrantException | RecordConflict e) {
                                result = false;
                            }

                            results.put(grant.getNode(), result);
                        });

                return results.values()
                        .stream()
                        .anyMatch(Boolean::booleanValue);
            });

            return Responses.ok(new RightsGrantResponse(results));
        } catch (ScopeNotFoundException e) {
            return Responses.notFound("scope not found");
        } catch (TransactionRollbackException e) {
            return Responses.conflict("all grants failed");
        }
    }

    private boolean doCheck(Grant grant) {
        try {
            accessGrantStrategy.checkAccess(grant);
            return true;
        } catch (AccessGrantException e) {
            return false;
        }
    }
}
