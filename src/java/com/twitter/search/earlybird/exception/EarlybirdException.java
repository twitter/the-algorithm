package com.twitter.search.earlybird.exception;

/**
 * General Earlybird exception class to use instead of the Java exception class.
 */
public class EarlybirdException extends Exception {
  public EarlybirdException(Throwable cause) {
    super(cause);
  }

  public EarlybirdException(String message) {
    super(message);
  }

  public EarlybirdException(String message, Throwable cause) {
    super(message, cause);
  }
}
