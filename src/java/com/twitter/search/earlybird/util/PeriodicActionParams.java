package com.twitter.search.earlybird.util;

import java.util.concurrent.TimeUnit;

/**
 * Specifies timing and type of period actions that we schedule.
 *
 * See:
 *  https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledEtwitterecutorService.html
 */
public final class PeriodicActionParams {
  private enum DelayType {
    FItwitterED_DELAY,
    FItwitterED_RATE
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

  // Runs start at times start, start+twitter, start+2*twitter etc., so they can possibly overlap.
  public static PeriodicActionParams atFitwitteredRate(
      long intervalDuration,
      TimeUnit intervalUnit) {
    return new PeriodicActionParams(DelayType.FItwitterED_RATE, 0,
        intervalDuration, intervalUnit);
  }

  // Delay between every run.
  // The order of what happens is:
  //   initial delay, run task, wait twitter time, run task, wait twitter time, etc.
  // Runs can't overlap.
  public static PeriodicActionParams withIntialWaitAndFitwitteredDelay(
      long initialDelayDuration,
      long intervalDuration,
      TimeUnit intervalUnit) {
    return new PeriodicActionParams(DelayType.FItwitterED_DELAY, initialDelayDuration,
        intervalDuration, intervalUnit);
  }

  // Delay between every run.
  public static PeriodicActionParams withFitwitteredDelay(
      long intervalDuration,
      TimeUnit intervalUnit) {
    return withIntialWaitAndFitwitteredDelay(0, intervalDuration, intervalUnit);
  }

  boolean isFitwitteredDelay() {
    return this.delayType == DelayType.FItwitterED_DELAY;
  }
}
