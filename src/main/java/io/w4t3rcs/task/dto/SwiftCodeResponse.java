package io.w4t3rcs.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
import java.util.List;

@Schema(description = "Response body for \"/v1/swift-codes/{code}\" (GET) endpoint")
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@JsonPropertyOrder({"address", "bankName", "countryISO2", "countryName", "isHeadquarter", "swiftCode", "branches"})
public class SwiftCodeResponse implements Serializable {
    @JsonProperty("swiftCode")
    private String code;
    private String bankName;
    private String address;
    @JsonProperty("countryISO2")
    private String countryIso2;
    private String countryName;
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIncludeProperties({"address", "bankName", "countryISO2", "isHeadquarter", "swiftCode"})
    private List<SwiftCodeResponse> branches;

    public static SwiftCodeResponse fromSwiftCode(@Valid SwiftCode swiftCode) {
        Country country = swiftCode.getCountry();
        List<SwiftCodeResponse> branches = swiftCode.getBranches() != null ? swiftCode.getBranches()
                .stream()
                .map(SwiftCodeResponse::fromSwiftCode)
                .toList() : null;
        return SwiftCodeResponse.builder()
                .code(swiftCode.getCode())
                .bankName(swiftCode.getBankName())
                .address(swiftCode.getAddress())
                .countryIso2(country.getIso2())
                .countryName(country.getName())
                .isHeadquarter(swiftCode.isHeadquarter())
                .branches(branches)
                .build();
    }
}
