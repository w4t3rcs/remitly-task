package io.w4t3rcs.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import io.w4t3rcs.task.entity.Country;
import io.w4t3rcs.task.entity.SwiftCode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "Request body for \"/v1/swift-codes\" (POST) endpoint")
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@JsonPropertyOrder({"address", "bankName", "countryISO2", "countryName", "swiftCode"})
public class SwiftCodeRequest implements Serializable {
    @JsonProperty("swiftCode")
    private String code;
    private String bankName;
    private String address;
    @JsonProperty("countryISO2")
    private String countryIso2;
    private String countryName;
    private boolean isHeadquarter;

    @Valid
    public SwiftCode toSwiftCode() {
        Country country = new Country(this.countryIso2.toUpperCase(), this.countryName.toUpperCase());
        return new SwiftCode(this.code, this.bankName, this.address, country, this.isHeadquarter, null);
    }
}
