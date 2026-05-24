package com.twitter.search.earlybird.exception;

public class ClientException extends Exception {
  public ClientException(Throwable t) {
    super(t);
  }

  public ClientException(String message) {
    super(message);
  }
}
