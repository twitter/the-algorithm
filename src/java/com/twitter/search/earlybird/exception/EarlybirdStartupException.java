package com.twitter.search.earlybird.exception;

/**
 * Thrown by code that is executed during startup and used to communicate to caller that startup
 * has failed. Generally results in shutting down of the server, but check on your own if you
 * need to.
 */
public class EarlybirdStartupException extends Exception {
  public EarlybirdStartupException(Throwable cause) {
    super(cause);
  }

  public EarlybirdStartupException(String message) {
    super(message);
  }

  public EarlybirdStartupException(String message, Throwable cause) {
    super(message, cause);
  }
}
