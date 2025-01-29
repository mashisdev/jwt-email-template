package com.mash.noteapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

        private String token;
        // private String type = "Bearer";
        private long expiresIn;
}
