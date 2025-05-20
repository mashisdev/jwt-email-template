package com.mashisdev.jwtemail.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

        private String token;
//        @JsonProperty("refresh_token")
//        private String refreshToken;
//        @JsonProperty("expires_in")
//        private long expiresIn;
}
