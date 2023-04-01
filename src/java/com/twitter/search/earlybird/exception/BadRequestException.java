package com.twitter.search.earlybird.exception;

public class BadRequestException extends Exception {
  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadRequestException(String message) {
    super(message);
  }
}
