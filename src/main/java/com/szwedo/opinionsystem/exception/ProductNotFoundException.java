package com.szwedo.opinionsystem.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(long id) {
    super("The product with id " + id + " does not found");
  }
}

