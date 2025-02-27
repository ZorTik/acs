package me.zort.acs.http.controller;

import lombok.RequiredArgsConstructor;
import me.zort.acs.AccessRecordService;
import me.zort.acs.definitions.DefinitionsService;
import me.zort.acs.exception.RecordConflict;
import me.zort.acs.http.dto.SubjectCreateRequest;
import me.zort.acs.http.dto.SubjectDto;
import me.zort.acs.http.entity.ResponseEntityWrapper;
import me.zort.acs.util.MessageConstants;
import me.zort.acs.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping
@Controller
public class SubjectsController {
    private final DefinitionsService definitionsService;
    private final AccessRecordService accessRecordService;

    @GetMapping("/scope/{id}/subject/{subject}")
    public ResponseEntityWrapper<SubjectDto> getSubject(@PathVariable("id") String id, @PathVariable("subject") String subjectId) {
        return Optional.ofNullable(definitionsService.getScope(id))
                .map(scope -> Optional.ofNullable(accessRecordService.getSubject(scope, subjectId))
                        .map(SubjectDto::new)
                        .map(Responses::ok)
                        .orElse(Responses.notFound("Subject not found")))
                .orElse(Responses.notFound(MessageConstants.SCOPE_NOT_FOUND));
    }

    @DeleteMapping("/scope/{id}/subject/{subject}")
    public ResponseEntityWrapper<Object> deleteSubject(@PathVariable("id") String id, @PathVariable("subject") String subjectId) {
        return Optional.ofNullable(definitionsService.getScope(id))
                .map(scope -> {
                    try {
                        accessRecordService.deleteSubject(scope, subjectId);
                        return Responses.ok();
                    } catch (RecordConflict e) {
                        return Responses.notFound("Subject not found");
                    }
                })
                .orElse(Responses.notFound(MessageConstants.SCOPE_NOT_FOUND));
    }

    @PostMapping("/subject/create")
    public ResponseEntityWrapper<SubjectDto> createSubject(@RequestBody SubjectCreateRequest body) {
        return Optional.ofNullable(definitionsService.getScope(body.getScope()))
                .map(scope -> {
                    try {
                        accessRecordService.createSubject(scope, body.getId());
                        return Responses.<SubjectDto>ok();
                    } catch (RecordConflict e) {
                        return Responses.<SubjectDto>conflict("Subject already exists");
                    }
                })
                .orElse(Responses.notFound(MessageConstants.SCOPE_NOT_FOUND));
    }
}
