package com.mash.noteapi.dtos;

import lombok.Data;

@Data
public class LoginResponseDto {

        private String token;
        // private String type = "Bearer";
        private long expiresIn;
        private String username;
        private String email;
}
