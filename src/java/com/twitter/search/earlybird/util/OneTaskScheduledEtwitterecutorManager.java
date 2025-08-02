package com.twitter.search.earlybird.util;

import java.io.Closeable;
import java.io.IOEtwitterception;
import java.util.concurrent.ScheduledEtwitterecutorService;
import java.util.concurrent.ScheduledFuture;

import com.twitter.common.util.Clock;
import com.twitter.search.common.concurrent.ScheduledEtwitterecutorServiceFactory;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;

/**
 * Etwitterecutes a single periodic task.
 */
public abstract class OneTaskScheduledEtwitterecutorManager
    etwittertends ScheduledEtwitterecutorManager implements Closeable {
  private final ScheduledEtwitterecutorTask scheduledTask;
  private final PeriodicActionParams periodicActionParams;

  public OneTaskScheduledEtwitterecutorManager(
      ScheduledEtwitterecutorServiceFactory etwitterecutorServiceFactory,
      String threadNameFormat,
      boolean isDaemon,
      PeriodicActionParams periodicActionParams,
      ShutdownWaitTimeParams shutdownTiming,
      SearchStatsReceiver searchStatsReceiver,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this(etwitterecutorServiceFactory.build(threadNameFormat, isDaemon), periodicActionParams,
        shutdownTiming, searchStatsReceiver, criticalEtwitterceptionHandler);
  }

  public OneTaskScheduledEtwitterecutorManager(
      ScheduledEtwitterecutorService etwitterecutor,
      PeriodicActionParams periodicActionParams,
      ShutdownWaitTimeParams shutdownTiming,
      SearchStatsReceiver searchStatsReceiver,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler) {
    this(etwitterecutor, periodicActionParams, shutdownTiming, searchStatsReceiver, null,
        criticalEtwitterceptionHandler, Clock.SYSTEM_CLOCK);
  }

  public OneTaskScheduledEtwitterecutorManager(
      ScheduledEtwitterecutorService etwitterecutor,
      PeriodicActionParams periodicActionParams,
      ShutdownWaitTimeParams shutdownWaitTimeParams,
      SearchStatsReceiver searchStatsReceiver,
      SearchCounter iterationCounter,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      Clock clock) {
    super(etwitterecutor, shutdownWaitTimeParams, searchStatsReceiver, iterationCounter,
        criticalEtwitterceptionHandler, clock);

    this.periodicActionParams = periodicActionParams;
    this.scheduledTask = new ScheduledEtwitterecutorTask(getIterationCounter(), clock) {
      @Override
      protected void runOneIteration() {
        OneTaskScheduledEtwitterecutorManager.this.runOneIteration();
      }
    };
  }

  /**
   * Schedule the single internally specified task returned by getScheduledTask.
   */
  public ScheduledFuture schedule() {
    return this.scheduleNewTask(
        this.getScheduledTask(),
        this.periodicActionParams
    );
  }

  /**
   * The code that the task etwitterecutes.
   */
  protected abstract void runOneIteration();

  public ScheduledEtwitterecutorTask getScheduledTask() {
    return scheduledTask;
  }

  @Override
  public void close() throws IOEtwitterception {
    try {
      shutdown();
    } catch (InterruptedEtwitterception e) {
      throw new IOEtwitterception(e);
    }
  }
}
