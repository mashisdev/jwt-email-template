package com.mashisdev.jwtemail.controller;

import com.mashisdev.jwtemail.dto.request.LoginRequestDto;
import com.mashisdev.jwtemail.dto.request.RegisterRequestDto;
import com.mashisdev.jwtemail.dto.request.VerifyRequestDto;
import com.mashisdev.jwtemail.model.User;
import com.mashisdev.jwtemail.dto.response.LoginResponseDto;
import com.mashisdev.jwtemail.services.AuthenticationService;
import com.mashisdev.jwtemail.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        User registeredUser = authenticationService.signup(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid LoginRequestDto loginRequestDto){
        User authenticatedUser = authenticationService.authenticate(loginRequestDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponseDto loginResponseDto = new LoginResponseDto(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponseDto);
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