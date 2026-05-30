# Nivora Finance - Authentication Module

## Purpose

The Authentication Module is responsible for:

* User registration
* Email verification through OTP
* User login
* JWT-based authentication
* Session management using Redis
* Route protection
* User logout
* Token revocation

Only verified users can access protected APIs.

---

# Authentication Flow

## 1. Signup

Endpoint:

POST /api/v1/auth/signup

What happens:

1. User submits name, email, and password.

2. Password is encrypted using BCrypt.

3. User is saved in PostgreSQL with:

   verified = false

4. A 6-digit OTP is generated.

5. OTP is stored in Redis for 5 minutes.

Result:

User account is created but cannot login yet.

---

## 2. OTP Verification

Endpoint:

POST /api/v1/auth/verify-otp

What happens:

1. User submits email and OTP.

2. OTP is fetched from Redis.

3. OTP is validated.

4. User verified status is updated:

   verified = true

5. OTP is removed from Redis.

Result:

User account becomes active.

---

## 3. Login

Endpoint:

POST /api/v1/auth/login

What happens:

1. User enters email and password.
2. System checks if user exists.
3. System checks if account is verified.
4. Password is validated.
5. JWT token is generated.
6. JWT is stored in Redis for 3 hours.
7. JWT is returned to the client.

Result:

User is authenticated.

---

# Route Types

## Public Routes

These routes do not require authentication.

POST /api/v1/auth/signup

POST /api/v1/auth/verify-otp

POST /api/v1/auth/login

Anyone can access these routes.

---

## Protected Routes

These routes require a valid JWT.

GET /api/v1/auth/me

POST /api/v1/auth/logout

A request without a valid JWT will be rejected.

---

# JWT Authentication Flow

Client sends:

Authorization: Bearer <jwt-token>

Request Flow:

Request
→ JwtFilter
→ Extract JWT
→ Validate JWT Signature
→ Extract Email
→ Check Redis Session
→ Authenticate User
→ Allow Request

If any step fails:

Request Rejected

---

# Redis Usage

## OTP Storage

Key Format:

otp:user@email.com

Example:

otp:harsh233390@gmail.com

Expiry:

5 Minutes

Purpose:

Stores temporary OTP for account verification.

---

## JWT Session Storage

Key Format:

jwt:user@email.com

Example:

jwt:harsh233390@gmail.com

Expiry:

3 Hours

Purpose:

Stores active user session.

---

# Current User Endpoint

Endpoint:

GET /api/v1/auth/me

Purpose:

Returns currently authenticated user information.

Response Example:

{
"id": 1,
"name": "Harsh",
"email": "[harsh233390@gmail.com](mailto:harsh233390@gmail.com)"
}

Password is never returned.

---

# Logout

Endpoint:

POST /api/v1/auth/logout

What happens:

1. User JWT is identified.
2. JWT session is removed from Redis.
3. Security context is cleared.

Result:

User is logged out.

Previously issued JWT tokens become invalid immediately.

---

# Security Features

## Password Encryption

BCrypt hashing is used before storing passwords.

---

## Email Verification

Only verified users can login.

---

## JWT Authentication

Protected routes require a valid JWT.

---

## Redis Session Validation

JWT must exist in Redis.

A valid JWT without a Redis session is rejected.

---

## Token Revocation

Logout immediately invalidates previously issued JWTs.

---

# Module Status

Status: COMPLETE

Implemented Features:

* Signup
* OTP Verification
* Login
* JWT Authentication
* Redis Session Management
* Protected Routes
* Current User Endpoint
* Logout
* Token Revocation
