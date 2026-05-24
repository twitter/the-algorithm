package com.twitter.search.earlybird.util;

import java.util.concurrent.TimeUnit;

/**
 * Specifies timing and type of period actions that we schedule.
 *
 * See:
 *  https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html
 */
public final class PeriodicActionParams {
  private enum DelayType {
    FIXED_DELAY,
    FIXED_RATE
  }

  private long initialDelayDuration;
  private long intervalDuration;
  private TimeUnit intervalUnit;
  private DelayType delayType;

  public long getInitialDelayDuration() {
    return initialDelayDuration;
  }

  public long getIntervalDuration() {
    return intervalDuration;
  }

  public TimeUnit getIntervalUnit() {
    return intervalUnit;
  }

  public DelayType getDelayType() {
    return delayType;
  }

  private PeriodicActionParams(
      DelayType delayType,
      long initialDelayDuration,
      long intervalDuration,
      TimeUnit intervalUnit) {
    this.delayType = delayType;
    this.intervalDuration = intervalDuration;
    this.initialDelayDuration = initialDelayDuration;
    this.intervalUnit = intervalUnit;
  }

  // Runs start at times start, start+X, start+2*X etc., so they can possibly overlap.
  public static PeriodicActionParams atFixedRate(
      long intervalDuration,
      TimeUnit intervalUnit) {
    return new PeriodicActionParams(DelayType.FIXED_RATE, 0,
        intervalDuration, intervalUnit);
  }

  // Delay between every run.
  // The order of what happens is:
  //   initial delay, run task, wait X time, run task, wait X time, etc.
  // Runs can't overlap.
  public static PeriodicActionParams withIntialWaitAndFixedDelay(
      long initialDelayDuration,
      long intervalDuration,
      TimeUnit intervalUnit) {
    return new PeriodicActionParams(DelayType.FIXED_DELAY, initialDelayDuration,
        intervalDuration, intervalUnit);
  }

  // Delay between every run.
  public static PeriodicActionParams withFixedDelay(
      long intervalDuration,
      TimeUnit intervalUnit) {
    return withIntialWaitAndFixedDelay(0, intervalDuration, intervalUnit);
  }

  boolean isFixedDelay() {
    return this.delayType == DelayType.FIXED_DELAY;
  }
}
