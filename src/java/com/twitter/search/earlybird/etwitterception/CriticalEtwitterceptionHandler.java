package com.twitter.search.earlybird.etwitterception;

import com.google.common.annotations.VisibleForTesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.twitter.search.common.config.Config;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.earlybird.EarlybirdStatus;

/**
 * Used for handling etwitterceptions considered critical.
 *
 * When you handle an etwitterception with this class, two things might happen.
 * 1. If earlybirds are still starting, we'll shut them down.
 * 2. If earlybirds have started, we'll increment a counter that will cause alerts.
 *
 * If you want to verify that your code handles etwitterceptions as you etwitterpect, you can use the
 * helper class EtwitterceptionCauser.
 */
public class CriticalEtwitterceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(CriticalEtwitterceptionHandler.class);
  private static final Marker FATAL = MarkerFactory.getMarker("FATAL");

  // This stat should remain at 0 during normal operations.
  // This stat being non-zero should trigger alerts.
  public static final SearchCounter CRITICAL_EtwitterCEPTION_COUNT =
      SearchCounter.etwitterport("fatal_etwitterception_count");

  public static final SearchCounter UNSAFE_MEMORY_ACCESS =
      SearchCounter.etwitterport("unsafe_memory_access");

  private Runnable shutdownHook;

  public void setShutdownHook(Runnable shutdownHook) {
    this.shutdownHook = shutdownHook;
  }

  /**
   * Handle a critical etwitterception.
   *
   * @param thrower Instance of the class where the etwitterception was thrown.
   * @param thrown The etwitterception.
   */
  public void handle(Object thrower, Throwable thrown) {
    if (thrown == null) {
      return;
    }

    try {
      handleFatalEtwitterception(thrower, thrown);
    } catch (Throwable e) {
      LOG.error("Unetwitterpected etwitterception in EarlybirdEtwitterceptionHandler.handle() while handling an "
                + "unetwitterpected etwitterception from " + thrower.getClass(), e);
    }
  }

  @VisibleForTesting
  boolean shouldIncrementFatalEtwitterceptionCounter(Throwable thrown) {
    // See D212952
    // We don't want to get pages when this happens.
    for (Throwable t = thrown; t != null; t = t.getCause()) {
      if (t instanceof InternalError && t.getMessage() != null
          && t.getMessage().contains("unsafe memory access operation")) {
        // Don't treat InternalError caused by unsafe memory access operation which is usually
        // triggered by SIGBUS for accessing a corrupted memory block.
        UNSAFE_MEMORY_ACCESS.increment();
        return false;
      }
    }

    return true;
  }

  /**
   * Handle an etwitterception that's considered fatal.
   *
   * @param thrower instance of the class where the etwitterception was thrown.
   * @param thrown The Error or Etwitterception.
   */
  private void handleFatalEtwitterception(Object thrower, Throwable thrown) {
    LOG.error(FATAL, "Fatal etwitterception in " + thrower.getClass() + ":", thrown);

    if (shouldIncrementFatalEtwitterceptionCounter(thrown)) {
      CRITICAL_EtwitterCEPTION_COUNT.increment();
    }

    if (EarlybirdStatus.isStarting()) {
      LOG.error(FATAL, "Got fatal etwitterception while starting up, etwitteriting ...");
      if (this.shutdownHook != null) {
        this.shutdownHook.run();
      } else {
        LOG.error("earlybirdServer not set, can't shut down.");
      }

      if (!Config.environmentIsTest()) {
        // Sleep for 3 minutes to allow the fatal etwitterception to be caught by observability.
        try {
          Thread.sleep(3 * 60 * 1000);
        } catch (InterruptedEtwitterception e) {
          LOG.error(FATAL, "interupted sleep while shutting down.");
        }
        LOG.info("Terminate JVM.");
        //CHECKSTYLE:OFF RegetwitterpSinglelineJava
        // See SEARCH-15256
        System.etwitterit(-1);
        //CHECKSTYLE:ON RegetwitterpSinglelineJava
      }
    }
  }
}
