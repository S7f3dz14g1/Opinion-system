package com.szwedo.opinionsystem.exception;

public class OpinionNotFoundException extends RuntimeException {
  public OpinionNotFoundException(long id) {
    super("The opinion with id " + id + " does not found");
  }
}