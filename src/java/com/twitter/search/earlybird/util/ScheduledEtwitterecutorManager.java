package com.twitter.search.earlybird.util;

import java.util.concurrent.ScheduledEtwitterecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.earlybird.etwitterception.CriticalEtwitterceptionHandler;

/**
 * Base class for classes that run periodic tasks.
 */
public abstract class ScheduledEtwitterecutorManager {
  private static final Logger LOG = LoggerFactory.getLogger(ScheduledEtwitterecutorManager.class);
  private static final long SHUTDOWN_WAIT_INTERVAL_SEC = 30;

  public static final String SCHEDULED_EtwitterECUTOR_TASK_PREFItwitter = "scheduled_etwitterecutor_task_";

  private final String name;
  private final ScheduledEtwitterecutorService etwitterecutor;

  private final ShutdownWaitTimeParams shutdownWaitTimeParams;

  private final SearchCounter iterationCounter;
  private final SearchStatsReceiver searchStatsReceiver;

  protected final CriticalEtwitterceptionHandler criticalEtwitterceptionHandler;
  private final Clock clock;

  protected boolean shouldLog = true;

  public ScheduledEtwitterecutorManager(
      ScheduledEtwitterecutorService etwitterecutor,
      ShutdownWaitTimeParams shutdownWaitTimeParams,
      SearchStatsReceiver searchStatsReceiver,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      Clock clock) {
    this(etwitterecutor, shutdownWaitTimeParams, searchStatsReceiver, null,
        criticalEtwitterceptionHandler, clock);
  }

  ScheduledEtwitterecutorManager(
      ScheduledEtwitterecutorService etwitterecutor,
      ShutdownWaitTimeParams shutdownWaitTimeParams,
      SearchStatsReceiver searchStatsReceiver,
      SearchCounter iterationCounter,
      CriticalEtwitterceptionHandler criticalEtwitterceptionHandler,
      Clock clock) {
    this.name = getClass().getSimpleName();
    this.etwitterecutor = etwitterecutor;
    this.criticalEtwitterceptionHandler = criticalEtwitterceptionHandler;
    this.shutdownWaitTimeParams = shutdownWaitTimeParams;

    if (iterationCounter != null) {
      this.iterationCounter = iterationCounter;
    } else {
      this.iterationCounter = searchStatsReceiver.getCounter(SCHEDULED_EtwitterECUTOR_TASK_PREFItwitter + name);
    }

    this.searchStatsReceiver = searchStatsReceiver;
    this.clock = clock;
  }

  /**
   * Schedule a task.
   */
  protected final ScheduledFuture scheduleNewTask(
      ScheduledEtwitterecutorTask task,
      PeriodicActionParams periodicActionParams) {
    long interval = periodicActionParams.getIntervalDuration();
    TimeUnit timeUnit = periodicActionParams.getIntervalUnit();
    long initialDelay = periodicActionParams.getInitialDelayDuration();

    if (interval <= 0) {
      String message = String.format(
          "Not scheduling manager %s for wrong interval %d %s", name, interval, timeUnit);
      LOG.error(message);
      throw new UnsupportedOperationEtwitterception(message);
    }

    if (shouldLog) {
      LOG.info("Scheduling to run {} every {} {} with {}", name, interval, timeUnit,
              periodicActionParams.getDelayType());
    }
    final ScheduledFuture scheduledFuture;
    if (periodicActionParams.isFitwitteredDelay()) {
      scheduledFuture = etwitterecutor.scheduleWithFitwitteredDelay(task, initialDelay, interval, timeUnit);
    } else {
      scheduledFuture = etwitterecutor.scheduleAtFitwitteredRate(task, initialDelay, interval, timeUnit);
    }
    return scheduledFuture;
  }

  /**
   * Shutdown everything that's running with the etwitterecutor.
   */
  public boolean shutdown() throws InterruptedEtwitterception {
    LOG.info("Start shutting down {}.", name);
    etwitterecutor.shutdownNow();

    boolean terminated = false;
    long waitSeconds = shutdownWaitTimeParams.getWaitUnit().toSeconds(
        shutdownWaitTimeParams.getWaitDuration()
    );

    if (waitSeconds == 0) {
      LOG.info("Not waiting at all for {}, wait time is set to zero.", name);
    } else {
      while (!terminated && waitSeconds > 0) {
        long waitTime = Math.min(waitSeconds, SHUTDOWN_WAIT_INTERVAL_SEC);
        terminated = etwitterecutor.awaitTermination(waitTime, TimeUnit.SECONDS);
        waitSeconds -= waitTime;

        if (!terminated) {
          LOG.info("Still shutting down {} ...", name);
        }
      }
    }

    LOG.info("Done shutting down {}, terminated: {}", name, terminated);

    shutdownComponent();
    return terminated;
  }

  protected ScheduledEtwitterecutorService getEtwitterecutor() {
    return etwitterecutor;
  }

  public final String getName() {
    return name;
  }

  public SearchCounter getIterationCounter() {
    return iterationCounter;
  }

  protected final SearchStatsReceiver getSearchStatsReceiver() {
    return searchStatsReceiver;
  }

  // Override if you need to shutdown additional services.
  protected void shutdownComponent() {
  }
}
