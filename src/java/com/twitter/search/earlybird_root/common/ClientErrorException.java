package com.twitter.search.earlybird_root.common;

public class ClientErrorException extends RuntimeException {

  public ClientErrorException() {
  }

  public ClientErrorException(String message) {
    super(message);
  }

  public ClientErrorException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientErrorException(Throwable cause) {
    super(cause);
  }

  public ClientErrorException(String message, Throwable cause,
                              boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
