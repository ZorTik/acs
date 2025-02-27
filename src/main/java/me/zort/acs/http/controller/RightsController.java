package me.zort.acs.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/rights")
@Controller
public class RightsController {
}
