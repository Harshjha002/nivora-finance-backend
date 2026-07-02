package com.nivora.nivora_finance_backend.rate_limit.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nivora.nivora_finance_backend.auth.entity.User;
import com.nivora.nivora_finance_backend.common.exception.RateLimitExceededException;
import com.nivora.nivora_finance_backend.common.exception.UnauthorizedException;
import com.nivora.nivora_finance_backend.rate_limit.constants.RateLimitConstants;
import com.nivora.nivora_finance_backend.rate_limit.service.RateLimitService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Public APIs (Rate limit by IP)
        if (path.equals("/api/v1/auth/login")) {

            String key = "rate_limit:login:" + request.getRemoteAddr();

            checkRateLimit(
                    key,
                    RateLimitConstants.LOGIN_LIMIT,
                    RateLimitConstants.LOGIN_WINDOW_SECONDS);

        } else if (path.equals("/api/v1/auth/signup")) {

            String key = "rate_limit:signup:" + request.getRemoteAddr();

            checkRateLimit(
                    key,
                    RateLimitConstants.SIGNUP_LIMIT,
                    RateLimitConstants.SIGNUP_WINDOW_SECONDS);

        } else if (path.equals("/api/v1/auth/verify-otp")) {

            String key = "rate_limit:verify_otp:" + request.getRemoteAddr();

            checkRateLimit(
                    key,
                    RateLimitConstants.VERIFY_OTP_LIMIT,
                    RateLimitConstants.VERIFY_OTP_WINDOW_SECONDS);

        }

        // Protected APIs (Rate limit by User)
        else if (path.equals("/api/v1/transactions/transfer")) {

            User user = getAuthenticatedUser();

            String key = "rate_limit:transfer:user:" + user.getId();

            checkRateLimit(
                    key,
                    RateLimitConstants.TRANSFER_LIMIT,
                    RateLimitConstants.TRANSFER_WINDOW_SECONDS);

        } else if (path.equals("/api/v1/qr/pay")) {

            User user = getAuthenticatedUser();

            String key = "rate_limit:qr_payment:user:" + user.getId();

            checkRateLimit(
                    key,
                    RateLimitConstants.QR_PAYMENT_LIMIT,
                    RateLimitConstants.QR_PAYMENT_WINDOW_SECONDS);
        }

        filterChain.doFilter(request, response);
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User user)) {

            throw new UnauthorizedException("Unauthorized.");
        }

        return user;
    }

    private void checkRateLimit(
            String key,
            int limit,
            long windowSeconds) {

        boolean allowed = rateLimitService.isAllowed(
                key,
                limit,
                windowSeconds);

        if (!allowed) {
            throw new RateLimitExceededException(
                    "Too many requests. Please try again later.");
        }
    }
}