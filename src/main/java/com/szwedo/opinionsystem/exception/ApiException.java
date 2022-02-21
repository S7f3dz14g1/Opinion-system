package com.szwedo.opinionsystem.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(String message, HttpStatus status, ZonedDateTime timeZone) {
  @Builder
  public ApiException {
  }
}

