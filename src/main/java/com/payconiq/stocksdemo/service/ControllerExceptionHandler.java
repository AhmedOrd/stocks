package com.payconiq.stocksdemo.service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;

import javax.validation.ConstraintViolationException;

import com.payconiq.stocksdemo.exception.DuplicateStockException;
import com.payconiq.stocksdemo.exception.StockNotFoundException;
import com.payconiq.stocksdemo.model.ErrorResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

    private ErrorResponseFactory errorResponseFactory;

    private static final String ERROR_DESCRIPTION = "Cloud not Create stock, stock name already exist";

    public ControllerExceptionHandler(ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<?> handelStockNotFoundRespons(StockNotFoundException exception) {
        log.debug("Handling [{}]", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(DuplicateStockException.class)
    public ResponseEntity<?> handelInternalServerError(DuplicateStockException exception) {
        log.debug("Handling [{}]", exception.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                                                    .errorCode(HttpStatus.BAD_REQUEST.value())
                                                    .informationDetails(ERROR_DESCRIPTION).build(),
                                     HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> toResponse(ConstraintViolationException exception) {
        log.debug("Handling [{}]", exception.getMessage());


        ErrorResponse errorResponse = exception.getConstraintViolations().stream()
                .map(violation -> errorResponseFactory.createErrorResponse(violation))
                .findFirst().orElse(errorResponseFactory.interalServerErrorResponse());
        return determineErrorType(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> toResponse(Exception e) {
        log.error("Unexpected exception occurred", e);

        if (e instanceof IOException) {
            return determineErrorType(errorResponseFactory.unknownBadRequestErrorResponse());
        } else if (e instanceof HttpMessageNotReadableException) {
            return determineErrorType(errorResponseFactory.unknownBadRequestErrorResponse());
        }
        return determineErrorType(errorResponseFactory.interalServerErrorResponse());
    }

    private ResponseEntity<?> determineErrorType(ErrorResponse errorResponses) {
        if (errorResponses.getErrorCode() == 500) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorResponses);
        }
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON).body(errorResponses);

    }
}
