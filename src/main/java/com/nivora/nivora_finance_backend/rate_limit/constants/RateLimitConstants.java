package com.nivora.nivora_finance_backend.rate_limit.constants;

public final class RateLimitConstants {

    private RateLimitConstants() {
    }

    // Limits
    public static final int LOGIN_LIMIT = 5;
    public static final int SIGNUP_LIMIT = 3;
    public static final int VERIFY_OTP_LIMIT = 5;
    public static final int TRANSFER_LIMIT = 20;
    public static final int QR_PAYMENT_LIMIT = 20;

    // Time Window (seconds)
    public static final long LOGIN_WINDOW_SECONDS = 60;
    public static final long SIGNUP_WINDOW_SECONDS = 60;
    public static final long VERIFY_OTP_WINDOW_SECONDS = 60;
    public static final long TRANSFER_WINDOW_SECONDS = 60;
    public static final long QR_PAYMENT_WINDOW_SECONDS = 60;
}