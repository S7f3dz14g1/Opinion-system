package com.szwedo.opinionsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
class ApiExceptionHandler {

  @ExceptionHandler(value = {ProductNotFoundException.class})
  public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException e) {
    HttpStatus badRequest = HttpStatus.NOT_FOUND;
    ApiException apiException = getApiException(badRequest, e.getMessage());
    return new ResponseEntity<>(apiException, badRequest);
  }

  @ExceptionHandler(value = {DatasourceFailureException.class})
  public ResponseEntity<Object> handleDatasourceFailure(DatasourceFailureException e) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ApiException apiException = getApiException(status, e.getMessage());
    return new ResponseEntity<>(apiException, status);
  }

  @ExceptionHandler(value = {OpinionNotFoundException.class})
  public ResponseEntity<Object> handleOpinionNotFoundException(OpinionNotFoundException e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ApiException apiException = getApiException(status, e.getMessage());
    return new ResponseEntity<>(apiException, status);
  }

  private ApiException getApiException(HttpStatus status, String message) {
    return ApiException.builder()
        .message(message)
        .status(status)
        .timeZone(ZonedDateTime.now(ZoneId.of("Z")))
        .build();
  }
}