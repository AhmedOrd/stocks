package com.payconiq.stocksdemo.service;

import java.util.function.Function;

import javax.validation.ConstraintViolation;
import javax.validation.Path;

import com.payconiq.stocksdemo.model.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ErrorResponseFactory {

    private static final int BAD_REQUEST_STATUS_CODE = 400;
    private final MessageSource messageSource;


    @Autowired
    public ErrorResponseFactory(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private ErrorResponse createBadRequestErrorResponses(ConstraintViolation constraintViolation) {
        final Path invalidParameter = constraintViolation.getPropertyPath();
        final String messageId = constraintViolation.getMessage();
        return toSimpleErrorResponse(formattedMessage(invalidParameter, messageId));
    }

    public ErrorResponse createErrorResponse(ConstraintViolation constraintViolation) {
        Function<ConstraintViolation, ErrorResponse> errorResponse = this::createBadRequestErrorResponses;

        return errorResponse.apply(constraintViolation);
    }

    public ErrorResponse interalServerErrorResponse() {
        return ErrorResponse.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).informationDetails("Technical error").build();
    }

    public ErrorResponse unknownBadRequestErrorResponse() {
        return getErrorResponse(BAD_REQUEST_STATUS_CODE, getHttpErrorInfoMessagePropertyValue("unknown.badrequest.error.reason"));
    }

    private ErrorResponse getErrorResponse(int httpStatusCode, String reason) {
        return toErrorResponse(httpStatusCode, reason);
    }

    private String formattedMessage(Path invalidParameter, String messageId) {
        return String.format("%1s: %2s", invalidParameter, getHttpErrorInfoMessagePropertyValue(messageId));
    }

    private String getHttpErrorInfoMessagePropertyValue(String message) {
        return messageSource.getMessage(message, null, message, null);
    }

    private ErrorResponse toErrorResponse(int status, String reason) {
        return ErrorResponse.builder().errorCode(status).informationDetails(reason).build();
    }

    private ErrorResponse toSimpleErrorResponse(String reason) {
        return ErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value()).informationDetails(reason).build();
    }

}
