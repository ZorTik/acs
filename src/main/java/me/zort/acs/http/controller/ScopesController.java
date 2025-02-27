package me.zort.acs.http.controller;

import lombok.RequiredArgsConstructor;
import me.zort.acs.definitions.DefinitionsService;
import me.zort.acs.http.dto.ScopeDto;
import me.zort.acs.http.entity.ResponseEntityWrapper;
import me.zort.acs.node.INode;
import me.zort.acs.definitions.source.DefinitionsSource;
import me.zort.acs.util.MessageConstants;
import me.zort.acs.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping
@Controller
public class ScopesController {
    private final DefinitionsService definitionsService;
    private final DefinitionsSource definitionsSource;

    @GetMapping("/scopes")
    public ResponseEntityWrapper<Set<ScopeDto>> getScopes() {
        return Responses.ok(definitionsService.getScopes()
                .stream()
                .map(ScopeDto::new)
                .collect(Collectors.toSet()));
    }

    @GetMapping("/scope/{id}")
    public ResponseEntityWrapper<ScopeDto> getScope(@PathVariable("id") String id) {
        return Optional.ofNullable(definitionsService.getScope(id))
                .map(scope -> Responses.ok(new ScopeDto(scope)))
                .orElse(Responses.notFound(MessageConstants.SCOPE_NOT_FOUND));
    }

    @GetMapping("/scope/{id}/nodes")
    public ResponseEntityWrapper<Set<String>> getNodes(@PathVariable("id") String id) {
        return Optional.ofNullable(definitionsService.getScope(id))
                .map(definitionsService::getTree)
                .map(tree -> tree.joinNodeValues(definitionsSource.getNodeDelimiter()))
                .map(Responses::ok)
                .orElse(Responses.notFound(MessageConstants.SCOPE_NOT_FOUND));
    }

    @GetMapping("/scope/{id}/tree")
    public ResponseEntityWrapper<INode> getTree(@PathVariable("id") String id) {
        return Optional.ofNullable(definitionsService.getScope(id))
                .map(definitionsService::getTree)
                .map(Responses::ok)
                .orElse(Responses.notFound(MessageConstants.SCOPE_NOT_FOUND));
    }
}
