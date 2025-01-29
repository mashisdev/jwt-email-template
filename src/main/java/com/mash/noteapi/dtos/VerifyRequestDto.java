package com.mash.noteapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyRequestDto {
    private String email;
    private String verificationCode;
}
