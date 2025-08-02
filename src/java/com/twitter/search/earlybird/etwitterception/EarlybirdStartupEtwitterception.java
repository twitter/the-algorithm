package com.twitter.search.earlybird.etwitterception;

/**
 * Thrown by code that is etwitterecuted during startup and used to communicate to caller that startup
 * has failed. Generally results in shutting down of the server, but check on your own if you
 * need to.
 */
public class EarlybirdStartupEtwitterception etwittertends Etwitterception {
  public EarlybirdStartupEtwitterception(Throwable cause) {
    super(cause);
  }

  public EarlybirdStartupEtwitterception(String message) {
    super(message);
  }

  public EarlybirdStartupEtwitterception(String message, Throwable cause) {
    super(message, cause);
  }
}
