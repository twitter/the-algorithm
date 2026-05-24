package com.twitter.search.earlybird.exception;

import java.io.IOException;

public class FlushVersionMismatchException extends IOException {
  public FlushVersionMismatchException(Throwable cause) {
    super(cause);
  }

  public FlushVersionMismatchException(String message) {
    super(message);
  }

  public FlushVersionMismatchException(String message, Throwable cause) {
    super(message, cause);
  }
}
