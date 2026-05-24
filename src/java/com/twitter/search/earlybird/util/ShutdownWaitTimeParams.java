package com.twitter.search.earlybird.util;

import java.util.concurrent.TimeUnit;

/**
 * Specifies how much time do we wait when shutting down a task.
 */
public class ShutdownWaitTimeParams {
  private long waitDuration;
  private TimeUnit waitUnit;

  public ShutdownWaitTimeParams(long waitDuration, TimeUnit waitUnit) {
    this.waitDuration = waitDuration;
    this.waitUnit = waitUnit;
  }

  public long getWaitDuration() {
    return waitDuration;
  }

  public TimeUnit getWaitUnit() {
    return waitUnit;
  }

  /**
   * Returns a ShutdownWaitTimeParams instance that instructs the caller to wait indefinitely for
   * the task to shut down.
   */
  public static ShutdownWaitTimeParams indefinitely() {
    return new ShutdownWaitTimeParams(Long.MAX_VALUE, TimeUnit.DAYS);
  }

  /**
   * Returns a ShutdownWaitTimeParams instance that instructs the caller to shut down the task
   * immediately.
   */
  public static ShutdownWaitTimeParams immediately() {
    return new ShutdownWaitTimeParams(0, TimeUnit.MILLISECONDS);
  }
}
