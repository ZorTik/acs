package me.zort.acs.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zort.acs.scope.Scope;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectImpl implements Subject {
    private String id;
    private Scope scope;

}
