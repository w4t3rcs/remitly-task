package io.w4t3rcs.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "Response body for \"/v1/swift-codes/{code}\" (POST, DELETE) endpoints")
@Data
@AllArgsConstructor @NoArgsConstructor
public class MessageSwiftCodeResponse implements Serializable {
    private String message;
}
