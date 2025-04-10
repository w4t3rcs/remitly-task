package io.w4t3rcs.task.dto;

import com.fasterxml.jackson.annotation.*;
import io.w4t3rcs.task.entity.Country;
import io.w4t3rcs.task.entity.SwiftCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwiftCodeResponse implements Serializable {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    @JsonProperty("swiftCode")
    private String code;
    @JsonIncludeProperties({"address", "bankName", "countryISO2", "isHeadquarter", "swiftCode"})
    private List<SwiftCodeResponse> branches;

    public static SwiftCodeResponse fromSwiftCode(SwiftCode swiftCode) {
        Country country = swiftCode.getCountry();
        List<SwiftCodeResponse> branches = swiftCode.getBranches() != null ? swiftCode.getBranches()
                .stream()
                .map(SwiftCodeResponse::fromSwiftCode)
                .toList() : null;
        return SwiftCodeResponse.builder()
                .code(swiftCode.getCode())
                .bankName(swiftCode.getBankName())
                .address(swiftCode.getAddress())
                .countryISO2(country.getISO2())
                .countryName(country.getName())
                .isHeadquarter(swiftCode.isHeadquarter())
                .branches(branches)
                .build();
    }
}
