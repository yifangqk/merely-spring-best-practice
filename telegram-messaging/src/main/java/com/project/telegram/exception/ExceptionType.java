package com.project.telegram.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType implements ResponseStatus {
    ACCOUNT_EXISTS("Phone already exists"),
    ACCOUNT_NOT_EXISTS("Phone not exists"),
    ACCOUNT_INVALID_PASSWORD("Phone password is invalid, Please try again"),
    ACCOUNT_NOT_APPROVED("Account not approved, Please contact administrator"),
    ACCOUNT_LOCKED("Account locked, Please contact administrator"),
    ACCOUNT_NOT_FOUND("Account not found"),
    ACCOUNT_ADMIN_CREATED("Admin account created"),
    POLL_NOT_EXISTS("Poll not exists"),
    INVALID_OPTION_INDEX("Invalid option index");

    private final String message;
}
