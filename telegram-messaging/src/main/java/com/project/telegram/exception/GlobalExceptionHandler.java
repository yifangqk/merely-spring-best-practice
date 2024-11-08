package com.project.telegram.exception;

import com.project.telegram.dto.GeneralResponse;
import com.project.telegram.messaging.AppTelegramMessenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final AppTelegramMessenger appTelegramMessenger;

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<GeneralResponse> handleBusinessException(BusinessException e) {
        log.info("BusinessException: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(GeneralResponse.fromError(e.getExceptionType().name(), e.getExceptionType().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<GeneralResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());

        return ResponseEntity.badRequest()
                .body(GeneralResponse.fromError(HttpStatus.BAD_REQUEST.name(), errorMessage));
    }

    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public final ResponseEntity<GeneralResponse> handleSecurityException(AuthenticationException e) {
        log.warn(HttpStatus.UNAUTHORIZED.name(), e);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(GeneralResponse.fromError(HttpStatus.UNAUTHORIZED.name(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<GeneralResponse> handleServerException(Exception e) {
        appTelegramMessenger.sendError(e);
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.name(), e);

        return ResponseEntity.internalServerError()
                .body(GeneralResponse.fromError(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage()));
    }
}
