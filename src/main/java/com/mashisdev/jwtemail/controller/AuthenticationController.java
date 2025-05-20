package com.mashisdev.jwtemail.controller;

import com.mashisdev.jwtemail.dto.request.auth.LoginRequestDto;
import com.mashisdev.jwtemail.dto.request.auth.RegisterRequestDto;
import com.mashisdev.jwtemail.dto.request.auth.VerifyRequestDto;
import com.mashisdev.jwtemail.mapper.UserMapper;
import com.mashisdev.jwtemail.model.User;
import com.mashisdev.jwtemail.model.UserEntity;
import com.mashisdev.jwtemail.dto.response.LoginResponseDto;
import com.mashisdev.jwtemail.services.AuthenticationService;
import com.mashisdev.jwtemail.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        User user = userMapper.registerRequestToUser(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authenticationService.authenticate(loginRequestDto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader){
        return AuthenticationService.refreshToken(authHeader);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody @Valid VerifyRequestDto verifyRequestDto) {
        authenticationService.verifyUser(verifyRequestDto);
        Map<String, String> response = Collections.singletonMap("verification", "Account verified successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend")
    public ResponseEntity<Map<String, String> > resendVerificationCode(@RequestBody @Valid VerifyRequestDto verifyRequestDto) {
            authenticationService.resendVerificationCode(verifyRequestDto);
            Map<String, String> response = Collections.singletonMap("verification", "Verification code sent");
            return ResponseEntity.ok(response);
    }
}