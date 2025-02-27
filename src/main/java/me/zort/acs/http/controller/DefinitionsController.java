package me.zort.acs.http.controller;

import lombok.RequiredArgsConstructor;
import me.zort.acs.DefinitionsService;
import me.zort.acs.http.entity.ResponseEntityWrapper;
import me.zort.acs.util.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/definitions")
@Controller
public class DefinitionsController {
    private final DefinitionsService definitionsService;

    @PostMapping("/refresh")
    public ResponseEntityWrapper<Object> refreshDefinitions() {
        definitionsService.refresh();

        return Responses.ok();
    }
}
