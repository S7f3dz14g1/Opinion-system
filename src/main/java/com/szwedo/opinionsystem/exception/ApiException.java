package com.szwedo.opinionsystem.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

record ApiException(String message, HttpStatus status, ZonedDateTime timeZone) {
  @Builder
  public ApiException {
  }
}