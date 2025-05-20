package com.mashisdev.jwtemail.services;

import com.mashisdev.jwtemail.dto.request.auth.LoginRequestDto;
import com.mashisdev.jwtemail.dto.request.auth.RegisterRequestDto;
import com.mashisdev.jwtemail.dto.request.auth.VerifyRequestDto;
import com.mashisdev.jwtemail.dto.request.user.UpdateUserDto;
import com.mashisdev.jwtemail.dto.response.LoginResponseDto;
import com.mashisdev.jwtemail.dto.response.UserDto;
import com.mashisdev.jwtemail.exception.auth.user.EmailAlreadyExistsException;
import com.mashisdev.jwtemail.exception.auth.user.UserNotFoundException;
import com.mashisdev.jwtemail.exception.auth.user.UsernameAlreadyExistsException;
import com.mashisdev.jwtemail.exception.auth.user.WrongEmailOrPasswordException;
import com.mashisdev.jwtemail.exception.auth.verification.*;
import com.mashisdev.jwtemail.mapper.UserMapper;
import com.mashisdev.jwtemail.model.Role;
import com.mashisdev.jwtemail.model.User;
import com.mashisdev.jwtemail.model.UserEntity;
import com.mashisdev.jwtemail.repository.SpringJpaRepository;
import com.mashisdev.jwtemail.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public static ResponseEntity<LoginResponseDto> refreshToken(String authHeader) {
        return null;
    }

    // User registration
    public User register(User user) {

        if(userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyExistsException("Email is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(false);
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(30));

        sendVerificationEmail(user);
        return userRepository.save(user);
    }

    // User authentication
    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new WrongEmailOrPasswordException("Wrong email or password"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new WrongEmailOrPasswordException("Wrong email or password");
        }

        if (!user.isEnabled()) {
            throw new AccountNotVerifiedException("Account not verified. Please verify your account.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        UserEntity userEntity = userMapper.userToUserEntity(user);

        String jwtToken = jwtService.generateToken(userEntity);

        return LoginResponseDto.builder().token(jwtToken).build();
    }

    // User verification

    public void verifyUser(VerifyRequestDto verifyRequestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(verifyRequestDto.getEmail());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        if (user.isEnabled()) {
            throw new AccountAlreadyVerifiedException("Account is already verified");
        }

        if (!user.getVerificationCode().equals(verifyRequestDto.getVerificationCode())) {
            throw new InvalidVerificationCodeException("Invalid verification code");
        }
        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new VerificationCodeExpiredException("Verification code has expired");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiresAt(null);
        userRepository.save(user);
    }

    public void resendVerificationCode(VerifyRequestDto verifyRequestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(verifyRequestDto.getEmail());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        if (user.isEnabled()) {
            throw new AccountAlreadyVerifiedException("Account is already verified");
        }

        if (user.getVerificationCodeExpiresAt().isAfter(LocalDateTime.now())) {
            throw new VerificationCodeStillValidException("Current verification code is still valid");
        }

        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(30));
        sendVerificationEmail(user);
        userRepository.save(user);
    }

    private void sendVerificationEmail(User user) {
        String subject = "Jwt Email Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private Integer generateVerificationCode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}
