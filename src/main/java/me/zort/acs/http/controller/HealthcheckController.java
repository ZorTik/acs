package me.zort.acs.http.controller;

import me.zort.acs.http.dto.HealthcheckResponse;
import me.zort.acs.http.dto.HealthStatus;
import me.zort.acs.http.entity.ResponseEntityWrapper;
import me.zort.acs.util.Responses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthcheckController {

    @GetMapping
    public ResponseEntityWrapper<HealthcheckResponse> healthCheck() {
        return Responses.ok(new HealthcheckResponse(HealthStatus.HEALTHY));
    }
}
