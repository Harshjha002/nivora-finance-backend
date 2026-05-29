package com.nivora.nivora_finance_backend.auth.service.impl;

import com.nivora.nivora_finance_backend.auth.dto.request.LoginRequest;
import com.nivora.nivora_finance_backend.auth.dto.request.OtpVerifyRequest;
import com.nivora.nivora_finance_backend.auth.dto.request.SignupRequest;
import com.nivora.nivora_finance_backend.auth.dto.response.AuthResponse;
import com.nivora.nivora_finance_backend.auth.dto.response.UserProfileResponse;
import com.nivora.nivora_finance_backend.auth.entity.User;
import com.nivora.nivora_finance_backend.auth.repository.UserRepository;
import com.nivora.nivora_finance_backend.auth.service.AuthService;
import com.nivora.nivora_finance_backend.common.exception.InvalidCredentialsException;
import com.nivora.nivora_finance_backend.common.exception.ResourceNotFoundException;
import com.nivora.nivora_finance_backend.common.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequest req) {

        String reqEmail = req.getEmail();
        String reqName = req.getName();
        String reqPassword = req.getPassword();

        boolean emailExists = repo.existsByEmail(reqEmail);
        if(emailExists){
            throw new UserAlreadyExistsException(
                    "Email already exists"
            );
        }

        String encodedPassword =
                passwordEncoder.encode(reqPassword);

        User newUser = User.builder()
                .name(reqName)
                .password(encodedPassword)
                .email(reqEmail)
                .verified(false)
                .build();

        repo.save(newUser);

        generateOtp();

    }

    @Override
    public AuthResponse verifyOtp(OtpVerifyRequest req) {
        return null;
    }

   @Override
public AuthResponse login(LoginRequest req) {

    User user = repo.findByEmail(req.getEmail())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found"
                    ));

    boolean passwordMatches =
            passwordEncoder.matches(
                    req.getPassword(),
                    user.getPassword()
            );

    if (!passwordMatches) {

        log.warn(
                "Invalid login attempt for email: {}",
                req.getEmail()
        );

        throw new InvalidCredentialsException(
                "Invalid credentials"
        );
    }

    log.info(
            "Login successful for email: {}",
            req.getEmail()
    );

    return AuthResponse.builder()
            .message("Login successful")
            .token(null)
            .build();
}
    @Override
    public void logout() {

    }

    @Override
    public UserProfileResponse getCurrentUser() {
        return null;
    }

    private String generateOtp() {
        return "otp generated";
    }
}
