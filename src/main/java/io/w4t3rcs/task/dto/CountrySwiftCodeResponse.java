package io.w4t3rcs.task.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import io.w4t3rcs.task.entity.Country;
import io.w4t3rcs.task.entity.SwiftCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Response body for \"/v1/swift-codes/country/{iso2}\" (GET) endpoint")
@Data
@AllArgsConstructor @NoArgsConstructor
@JsonPropertyOrder({"countryISO2", "countryName", "swiftCodes"})
public class CountrySwiftCodeResponse implements Serializable {
    @JsonProperty("countryISO2")
    private String countryIso2;
    private String countryName;
    @JsonIncludeProperties({"address", "bankName", "countryISO2", "isHeadquarter", "swiftCode"})
    private List<SwiftCodeResponse> swiftCodes;

    public static CountrySwiftCodeResponse fromSwiftCodes(List<SwiftCode> swiftCodes) {
        CountrySwiftCodeResponse response = new CountrySwiftCodeResponse();
        List<SwiftCodeResponse> swiftCodeResponses = new ArrayList<>();
        SwiftCode firstSwiftCode = swiftCodes.get(0);
        Country country = firstSwiftCode.getCountry();
        response.setCountryIso2(country.getIso2());
        response.setCountryName(country.getName());
        swiftCodeResponses.add(SwiftCodeResponse.fromSwiftCode(firstSwiftCode));
        for (int i = 1; i < swiftCodes.size(); i++) {
            SwiftCode swiftCode = swiftCodes.get(i);
            swiftCodeResponses.add(SwiftCodeResponse.fromSwiftCode(swiftCode));
        }
        response.setSwiftCodes(swiftCodeResponses);
        return response;
    }
}
