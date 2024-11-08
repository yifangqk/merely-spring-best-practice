package com.project.telegram.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class BusinessException extends RuntimeException {
    private final ExceptionType exceptionType;
}
