package com.nivora.nivora_finance_backend.auth.service.impl;

import com.nivora.nivora_finance_backend.auth.dto.request.LoginRequest;
import com.nivora.nivora_finance_backend.auth.dto.request.OtpVerifyRequest;
import com.nivora.nivora_finance_backend.auth.dto.request.SignupRequest;
import com.nivora.nivora_finance_backend.auth.dto.response.AuthResponse;
import com.nivora.nivora_finance_backend.auth.dto.response.UserProfileResponse;
import com.nivora.nivora_finance_backend.auth.service.AuthService;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void signup(SignupRequest req) {

    }

    @Override
    public AuthResponse verifyOtp(OtpVerifyRequest req) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public UserProfileResponse getCurrentUser() {
        return null;
    }

    private String generateOtp() {
        return "";
    }
}
