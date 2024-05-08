package org.clearsolutions.restapi.controller;

import com.fasterxml.jackson.core.JsonParseException;
import org.clearsolutions.restapi.dto.Error;
import org.clearsolutions.restapi.dto.ErrorDto;
import org.clearsolutions.restapi.dto.ErrorsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestControllerAdvice
public class ControllerAdvice {
    private static final String VALIDATION_FIELD_ERROR_MESSAGE = "The field '%s' %s";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorsDto handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Error> errors = ex.getBindingResult().getAllErrors().stream()
                .map(ControllerAdvice::getError)
                .collect(Collectors.toList());

        return getErrorsDto(errors, HttpStatus.BAD_REQUEST.value());
    }

    private static Error getError(ObjectError objectError) {
        Error error;

        if (objectError instanceof FieldError fieldError) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            error = getError(format(VALIDATION_FIELD_ERROR_MESSAGE, fieldName, errorMessage));
        } else {
            String errorMessage = objectError.getDefaultMessage();

            error = getError(errorMessage);
        }

        return error;
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorDto> handleHttpClientErrorExceptions(HttpClientErrorException ex) {
        int statusCode = ex.getStatusCode().value();
        String errorDetail = ex.getStatusText();

        return ResponseEntity.status(statusCode).body(
                getErrorDto(getError(errorDetail), statusCode));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), getError(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto handleAllExceptions(Exception ex) {
        return new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), getError(ex.getMessage()));
    }


    protected static ErrorDto getErrorDto(Error error, Integer code) {
        return new ErrorDto(code, error);
    }

    protected static Error getError(String detail) {
        return new Error(detail);
    }

    protected static ErrorsDto getErrorsDto(List<Error> errors, Integer statusCode) {
        return new ErrorsDto(statusCode, errors);
    }
}
