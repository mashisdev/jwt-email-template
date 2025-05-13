package com.mashisdev.jwtemail.controllers;

import com.mashisdev.jwtemail.dtos.requests.LoginRequestDto;
import com.mashisdev.jwtemail.dtos.requests.RegisterRequestDto;
import com.mashisdev.jwtemail.dtos.requests.VerifyRequestDto;
import com.mashisdev.jwtemail.entities.User;
import com.mashisdev.jwtemail.dtos.responses.LoginResponseDto;
import com.mashisdev.jwtemail.services.AuthenticationService;
import com.mashisdev.jwtemail.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        User registeredUser = authenticationService.signup(registerRequestDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid LoginRequestDto loginRequestDto){
        User authenticatedUser = authenticationService.authenticate(loginRequestDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponseDto loginResponseDto = new LoginResponseDto(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody @Valid VerifyRequestDto verifyRequestDto) {
        try {
            authenticationService.verifyUser(verifyRequestDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}