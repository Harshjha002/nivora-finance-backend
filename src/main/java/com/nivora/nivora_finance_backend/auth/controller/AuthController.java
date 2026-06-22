package com.nivora.nivora_finance_backend.auth.controller;

import com.nivora.nivora_finance_backend.auth.dto.request.LoginRequest;
import com.nivora.nivora_finance_backend.auth.dto.request.OtpVerifyRequest;
import com.nivora.nivora_finance_backend.auth.dto.request.SignupRequest;
import com.nivora.nivora_finance_backend.auth.dto.response.AuthResponse;
import com.nivora.nivora_finance_backend.auth.dto.response.UserProfileResponse;
import com.nivora.nivora_finance_backend.auth.dto.response.UserSearchResponse;
import com.nivora.nivora_finance_backend.auth.service.AuthService;
import com.nivora.nivora_finance_backend.notification.service.EmailService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    // signup
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody SignupRequest req) {

        authService.signup(req);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .message("Signup successful")
                        .token(null)
                        .build());
    }

    // verify otp
    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(
            @RequestBody OtpVerifyRequest req) {

        AuthResponse res = authService.verifyOtp(req);

        return ResponseEntity.ok(res);
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest req) {

        AuthResponse res = authService.login(req);

        return ResponseEntity.ok(res);
    }

    // logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        authService.logout();

        return ResponseEntity.ok(
                "Logout successful");
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser() {

        UserProfileResponse res = authService.getCurrentUser();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserSearchResponse>> searchUsers(
            @RequestParam String keyword) {

        List<UserSearchResponse> users = authService.searchUsers(keyword);

        return ResponseEntity.ok(users);
    }

}
