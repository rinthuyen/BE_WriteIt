package com.writeit.write_it.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ApiError {

    AUTHENTICATION_FAILED("AUTH_001", HttpStatus.UNAUTHORIZED, "Authentication failed."),
    ACCESS_DENIED       ("AUTH_002", HttpStatus.FORBIDDEN, "Access denied."),
    CREDENTIALS_INVALID ("AUTH_003", HttpStatus.UNAUTHORIZED, "Invalid username or password."),
    USER_DEACTIVATED    ("AUTH_004", HttpStatus.FORBIDDEN, "User is disabled"),
    ACCOUNT_LOCKED      ("AUTH_005", HttpStatus.FORBIDDEN, "Account is locked"),
    ACCOUNT_EXPIRED     ("AUTH_006", HttpStatus.FORBIDDEN, "Account is expired"),
    CREDENTIALS_EXPIRED ("AUTH_007", HttpStatus.FORBIDDEN, "Credentials are expired"),
    CURRENT_PASSWORD_INVALID ("AUTH_008", HttpStatus.UNPROCESSABLE_ENTITY, "Current password is incorrect."),
    NEW_PASSWORD_SAME_AS_OLD ("AUTH_009", HttpStatus.UNPROCESSABLE_ENTITY, "New password is same as old password."),

    
    VALIDATION_FAILED   ("REQ_001", HttpStatus.BAD_REQUEST, "Validation failed."),
    REQ_BODY_INVALID    ("REQ_002", HttpStatus.BAD_REQUEST, "Malformed request body."),

    USERNAME_ALREADY_EXISTS ("USER_001", HttpStatus.CONFLICT, "Username already exists."),
    EMAIL_ALREADY_EXISTS    ("USER_002", HttpStatus.CONFLICT, "Email already exists."),
    USER_NOT_FOUND          ("USER_404", HttpStatus.NOT_FOUND, "User not found."),

    ACCESS_TOKEN_EXPIRED        ("TOK_001", HttpStatus.UNAUTHORIZED,     "Access token expired."),
    ACCESS_TOKEN_INVALID        ("TOK_002", HttpStatus.UNAUTHORIZED,     "Access token invalid."),
    SINGLE_USE_TOKEN_INVALID    ("TOK_003", HttpStatus.UNAUTHORIZED,     "Single use token invalid."),
    REFRESH_TOKEN_INVALID       ("TOK_004", HttpStatus.UNAUTHORIZED,     "Invalid refresh token."),
    REFRESH_TOKEN_EXPIRED       ("TOK_005", HttpStatus.UNAUTHORIZED,     "Expired refresh token."),
    REFRESH_TOKEN_REVOKED       ("TOK_006", HttpStatus.UNAUTHORIZED,     "Revoked refresh token."),
    TOKEN_PURPOSE_INVALID       ("TOK_007", HttpStatus.UNPROCESSABLE_ENTITY, "Invalid token purpose."),

    INTERNAL_ERROR("", HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private String code;
    private HttpStatus status;
    private String message;

    private ApiError(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
