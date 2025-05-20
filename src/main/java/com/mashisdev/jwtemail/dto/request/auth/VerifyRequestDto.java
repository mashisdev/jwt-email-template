package com.mashisdev.jwtemail.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Verification code is required")
    @Min(value = 100000, message = "Verification code must be a 6-digit number")
    @Max(value = 999999, message = "Verification code must be a 6-digit number")
    @JsonProperty("verification_code")
    private Integer verificationCode;
}
