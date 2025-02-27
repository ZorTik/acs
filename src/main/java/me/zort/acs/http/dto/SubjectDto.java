package me.zort.acs.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SubjectDto {
    @Nullable
    private String id;
    private String scope;

}
