package io.w4t3rcs.task.controller;

import io.w4t3rcs.task.dto.CountrySwiftCodeResponse;
import io.w4t3rcs.task.dto.MessageSwiftCodeResponse;
import io.w4t3rcs.task.dto.SwiftCodeRequest;
import io.w4t3rcs.task.dto.SwiftCodeResponse;
import io.w4t3rcs.task.entity.SwiftCode;
import io.w4t3rcs.task.service.SwiftCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/swift-codes")
@RequiredArgsConstructor
public class SwiftCodeController {
    private final SwiftCodeService swiftCodeService;

    @GetMapping
    public List<SwiftCodeResponse> getSwiftCodes() {
        return swiftCodeService.getSwiftCodes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SwiftCodeResponse> getSwiftCode(@PathVariable String id) {
        return ResponseEntity.ok(swiftCodeService.getSwiftCode(id));
    }

    @GetMapping("/country/{iso2}")
    public ResponseEntity<CountrySwiftCodeResponse> getSwiftCodeByCountry(@PathVariable String iso2) {
        return ResponseEntity.ok(swiftCodeService.getSwiftCodeByCountry(iso2));
    }

    @PostMapping
    public ResponseEntity<MessageSwiftCodeResponse> postSwiftCode(@RequestBody SwiftCodeRequest request) {
        return ResponseEntity.ok(swiftCodeService.createSwiftCode(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageSwiftCodeResponse> deleteSwiftCode(@PathVariable String id) {
        return ResponseEntity.ok(swiftCodeService.deleteSwiftCode(id));
    }
}
