package com.twitter.search.earlybird.exception;

public class TransientException extends Exception {
  public TransientException(Throwable t) {
    super(t);
  }

  public TransientException(String message, Throwable cause) {
    super(message, cause);
  }

  public TransientException(String message) {
    super(message);
  }
}
