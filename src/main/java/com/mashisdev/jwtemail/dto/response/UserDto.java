package com.mashisdev.jwtemail.dto.response;

import com.mashisdev.jwtemail.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.UUID;

public class UserDto {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
}
