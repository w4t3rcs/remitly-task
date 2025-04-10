package io.w4t3rcs.task.service;

import io.w4t3rcs.task.dto.CountrySwiftCodeResponse;
import io.w4t3rcs.task.dto.MessageSwiftCodeResponse;
import io.w4t3rcs.task.dto.SwiftCodeRequest;
import io.w4t3rcs.task.dto.SwiftCodeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SwiftCodeService {
    List<SwiftCodeResponse> getSwiftCodes();

    SwiftCodeResponse getSwiftCode(String code);

    CountrySwiftCodeResponse getSwiftCodeByCountry(String iso2);

    MessageSwiftCodeResponse createSwiftCode(SwiftCodeRequest request);

    MessageSwiftCodeResponse deleteSwiftCode(String code);
}
