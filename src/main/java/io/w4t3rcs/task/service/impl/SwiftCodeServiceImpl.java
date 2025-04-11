package io.w4t3rcs.task.service.impl;

import io.w4t3rcs.task.dto.CountrySwiftCodeResponse;
import io.w4t3rcs.task.dto.MessageSwiftCodeResponse;
import io.w4t3rcs.task.dto.SwiftCodeRequest;
import io.w4t3rcs.task.dto.SwiftCodeResponse;
import io.w4t3rcs.task.entity.SwiftCode;
import io.w4t3rcs.task.exception.NotFoundException;
import io.w4t3rcs.task.repository.SwiftCodeRepository;
import io.w4t3rcs.task.service.SwiftCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SwiftCodeServiceImpl implements SwiftCodeService {
    private final SwiftCodeRepository swiftCodeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SwiftCodeResponse> getSwiftCodes() {
        return swiftCodeRepository.findAll()
                .stream()
                .map(SwiftCodeResponse::fromSwiftCode)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "swiftCodesCache", key = "#code")
    public SwiftCodeResponse getSwiftCode(String code) {
        return swiftCodeRepository.findById(code)
                .map(SwiftCodeResponse::fromSwiftCode)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public CountrySwiftCodeResponse getSwiftCodeByCountry(String iso2) {
        List<SwiftCode> swiftCodes = swiftCodeRepository.findAllByCountry_Iso2(iso2);
        return CountrySwiftCodeResponse.fromSwiftCodes(swiftCodes);
    }

    @Override
    @Transactional
    public MessageSwiftCodeResponse createSwiftCode(SwiftCodeRequest request) {
        SwiftCode swiftCode = request.toSwiftCode();
        String code = swiftCode.getCode();
        String codePrefix = code.substring(0, 7);
        if (swiftCode.isHeadquarter() || code.endsWith("XXX")) {
            saveHeadquarterSwiftCode(swiftCode, codePrefix);
        } else {
            saveBranchSwiftCode(swiftCode, codePrefix);
        }

        return new MessageSwiftCodeResponse("Swift code is successfully created - " + code);
    }

    private void saveHeadquarterSwiftCode(SwiftCode swiftCode, String codePrefix) {
        swiftCode.setHeadquarter(true);
        List<SwiftCode> branches = swiftCodeRepository.findAllByCodeContainingAndCodeNotContaining(codePrefix, "XXX");
        swiftCode.setBranches(branches);
        swiftCodeRepository.save(swiftCode);
    }

    private void saveBranchSwiftCode(SwiftCode swiftCode, String codePrefix) {
        List<SwiftCode> headquarters = swiftCodeRepository.findAllByCodeContainingAndCodeContaining(codePrefix, "XXX");
        swiftCodeRepository.save(swiftCode);
        headquarters.forEach(headquarter -> {
            List<SwiftCode> branches = headquarter.getBranches();
            if (branches == null) branches = new ArrayList<>();
            branches.add(swiftCode);
            headquarter.setBranches(branches);
            swiftCodeRepository.save(headquarter);
        });
    }

    @Override
    @Transactional
    @Caching(evict = @CacheEvict(value = "swiftCodesCache", key = "#code"))
    public MessageSwiftCodeResponse deleteSwiftCode(String code) {
        swiftCodeRepository.deleteById(code);
        return new MessageSwiftCodeResponse("Swift code is successfully deleted - " + code);
    }
}
