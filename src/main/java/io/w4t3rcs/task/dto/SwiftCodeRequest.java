package io.w4t3rcs.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.w4t3rcs.task.entity.Country;
import io.w4t3rcs.task.entity.SwiftCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class SwiftCodeRequest implements Serializable {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    @JsonProperty("swiftCode")
    private String code;

    public SwiftCode toSwiftCode() {
        Country country = new Country(this.countryISO2.toUpperCase(), this.countryName.toUpperCase());
        return new SwiftCode(this.code, this.bankName, this.address, country, this.isHeadquarter, null);
    }
}
