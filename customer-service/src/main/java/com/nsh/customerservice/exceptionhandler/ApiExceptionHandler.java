package com.nsh.customerservice.exceptionhandler;

import com.nsh.customerservice.exceptionhandler.exceptions.CustomerAlreadyExist;
import com.nsh.customerservice.exceptionhandler.exceptions.NotFoundException;
import com.nsh.customerservice.exceptionhandler.payload.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public <T extends BindException> ResponseEntity<ExceptionResponse> handleValidationException(final T e) {

        log.info("**ApiExceptionHandler controller, handle validation exception*\n");
        final var badRequest = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .msg("*" + Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage() + "!**")
                        .httpStatus(badRequest)
                        .timestamp(ZonedDateTime
                                .now(ZoneId.systemDefault()))
                        .build(), badRequest);
    }

    @ExceptionHandler({CustomerAlreadyExist.class, ConstraintViolationException.class, NotFoundException.class})
    public<T extends RuntimeException> ResponseEntity<ExceptionResponse> handleArithmeticException(final T ex) {
        return new ResponseEntity<> (ExceptionResponse.builder().msg(ex.getMessage()).timestamp(ZonedDateTime.now()).httpStatus(HttpStatus.BAD_REQUEST).build(),HttpStatus.BAD_REQUEST);
    }
}
