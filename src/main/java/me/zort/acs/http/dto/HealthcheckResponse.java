package me.zort.acs.http.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HealthcheckResponse {
    private final HealthStatus status;

    public HealthcheckResponse(HealthStatus status) {
        this.status = status;
    }
}
