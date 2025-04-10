package io.w4t3rcs.task.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class Country {
    @Length(min = 2, max = 2)
    private String iso2;
    @NotBlank
    private String name;
}
