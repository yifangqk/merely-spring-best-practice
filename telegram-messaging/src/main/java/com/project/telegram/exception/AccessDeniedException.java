package com.project.telegram.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to indicate that the current user doesn't have the right to access certain resources
 * The resources could be classes, functions...
 */

@Getter
@AllArgsConstructor
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {
    private String message;
}
