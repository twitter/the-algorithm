package com.twitter.search.earlybird.partition;

/**
 * Exception used to cause a ScheduledExecutorService to stop executing. Used when the
 * success condition of the class has been achieved.
 */
public class FlowControlException extends RuntimeException {

  public FlowControlException() {
    super();
  }

  public FlowControlException(String message) {
    super(message);
  }
}
