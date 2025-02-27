package me.zort.acs.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RightsGrantRequest implements GrantRequest { // TODO: Validace vstupu
    private SubjectDto from;
    private SubjectDto to;
    private Set<String> nodes;

}
