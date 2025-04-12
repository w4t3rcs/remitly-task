package io.w4t3rcs.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.w4t3rcs.task.dto.CountrySwiftCodeResponse;
import io.w4t3rcs.task.dto.MessageSwiftCodeResponse;
import io.w4t3rcs.task.dto.SwiftCodeRequest;
import io.w4t3rcs.task.dto.SwiftCodeResponse;
import io.w4t3rcs.task.service.SwiftCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "SWIFT codes controller", description = "Default REST endpoint for SWIFT codes")
@RestController
@RequestMapping("/v1/swift-codes")
@RequiredArgsConstructor
public class SwiftCodeController {
    private final SwiftCodeService swiftCodeService;

    @Operation(summary = "Getting SWIFT code by its code")
    @ApiResponse(responseCode = "200", description = "SWIFT code is successfully found")
    @GetMapping("/{code}")
    public ResponseEntity<SwiftCodeResponse> getSwiftCode(@PathVariable String code) {
        return ResponseEntity.ok(swiftCodeService.getSwiftCode(code));
    }

    @Operation(summary = "Getting SWIFT codes by their common country ISO2 code")
    @ApiResponse(responseCode = "200", description = "SWIFT codes are successfully found")
    @GetMapping("/country/{iso2}")
    public ResponseEntity<CountrySwiftCodeResponse> getSwiftCodeByCountry(@PathVariable String iso2) {
        return ResponseEntity.ok(swiftCodeService.getSwiftCodeByCountry(iso2));
    }

    @ApiResponse(responseCode = "200", description = "SWIFT code is successfully saved")
    @Operation(summary = "Creating and saving to the database new SWIFT code")
    @PostMapping
    public ResponseEntity<MessageSwiftCodeResponse> postSwiftCode(@Valid @RequestBody SwiftCodeRequest request) {
        return ResponseEntity.ok(swiftCodeService.createSwiftCode(request));
    }

    @ApiResponse(responseCode = "200", description = "SWIFT code is successfully deleted")
    @Operation(summary = "Removing SWIFT code from the database by its code")
    @DeleteMapping("/{code}")
    public ResponseEntity<MessageSwiftCodeResponse> deleteSwiftCode(@PathVariable String code) {
        return ResponseEntity.ok(swiftCodeService.deleteSwiftCode(code));
    }
}
