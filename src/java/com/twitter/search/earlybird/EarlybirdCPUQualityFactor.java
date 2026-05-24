package com.twitter.search.earlybird;

import com.google.common.annotations.VisibleForTesting;
import com.sun.management.OperatingSystemMXBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.decider.Decider;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchStatsReceiver;

/**
 * Manages the quality factor for an Earlybird based on CPU usage.
 */
public class EarlybirdCPUQualityFactor implements QualityFactor {
  public static final String ENABLE_QUALITY_FACTOR_DECIDER = "enable_quality_factor";
  public static final String OVERRIDE_QUALITY_FACTOR_DECIDER = "override_quality_factor";

  @VisibleForTesting
  protected static final double CPU_USAGE_THRESHOLD = 0.8;
  @VisibleForTesting
  protected static final double MAX_QF_INCREMENT = 0.5;
  @VisibleForTesting
  protected static final double MAX_QF_DECREMENT = 0.1;
  @VisibleForTesting
  protected static final double MAX_CPU_USAGE = 1.0;

  private static final Logger QUALITY_FACTOR_LOG =
      LoggerFactory.getLogger(EarlybirdCPUQualityFactor.class);
  private static final Logger EARLYBIRD_LOG = LoggerFactory.getLogger(Earlybird.class);

  /**
   * Tracks the real, underlying CPU QF value, regardless of the decider enabling
   * it.
   */
  @VisibleForTesting
  protected static final String UNDERLYING_CPU_QF_GUAGE = "underlying_cpu_quality_factor";

  /**
   * Reports the QF actually used to degrade Earlybirds.
   */
  @VisibleForTesting
  protected static final String CPU_QF_GUAGE = "cpu_quality_factor";

  private static final int SAMPLING_WINDOW_MILLIS = 60 * 1000;   // one minute


  private double qualityFactor = 1;
  private double previousQualityFactor = 1;

  private final SearchDecider decider;
  private final OperatingSystemMXBean operatingSystemMXBean;

  public EarlybirdCPUQualityFactor(
      Decider decider,
      OperatingSystemMXBean operatingSystemMXBean,
      SearchStatsReceiver searchStatsReceiver) {
    this.decider = new SearchDecider(decider);
    this.operatingSystemMXBean = operatingSystemMXBean;

    searchStatsReceiver.getCustomGauge(UNDERLYING_CPU_QF_GUAGE, () -> qualityFactor);
    searchStatsReceiver.getCustomGauge(CPU_QF_GUAGE, this::get);
  }

  /**
   * Updates the current quality factor based on CPU usage.
   */
  @VisibleForTesting
  protected void update() {
    previousQualityFactor = qualityFactor;

    double cpuUsage = operatingSystemMXBean.getSystemCpuLoad();

    if (cpuUsage < CPU_USAGE_THRESHOLD) {
      double increment =
          ((CPU_USAGE_THRESHOLD - cpuUsage) / CPU_USAGE_THRESHOLD) * MAX_QF_INCREMENT;
      qualityFactor = Math.min(1, qualityFactor + increment);
    } else {
      double decrement =
          ((cpuUsage - CPU_USAGE_THRESHOLD) / (MAX_CPU_USAGE - CPU_USAGE_THRESHOLD))
              * MAX_QF_DECREMENT;
      qualityFactor = Math.max(0, qualityFactor - decrement);
    }

    if (!qualityFactorChanged()) {
      return;
    }

    QUALITY_FACTOR_LOG.info(
        String.format("CPU: %.2f Quality Factor: %.2f", cpuUsage, qualityFactor));

    if (!enabled()) {
      return;
    }

    if (degradationBegan()) {
      EARLYBIRD_LOG.info("Service degradation began.");
    }

    if (degradationEnded()) {
      EARLYBIRD_LOG.info("Service degradation ended.");
    }
  }

  @Override
  public double get() {
    if (!enabled()) {
      return 1;
    }

    if (isOverridden()) {
      return override();
    }

    return qualityFactor;
  }

  @Override
  public void startUpdates() {
    new Thread(() -> {
      while (true) {
        update();
        try {
          Thread.sleep(SAMPLING_WINDOW_MILLIS);
        } catch (InterruptedException e) {
          QUALITY_FACTOR_LOG.warn(
              "Quality factoring thread interrupted during sleep between updates", e);
        }
      }
    }).start();
  }

  /**
   * Returns true if quality factoring is enabled by the decider.
   * @return
   */
  private boolean enabled() {
    return decider != null && decider.isAvailable(ENABLE_QUALITY_FACTOR_DECIDER);
  }

  /**
   * Returns true if a decider has overridden the quality factor.
   * @return
   */
  private boolean isOverridden() {
    return decider != null && decider.getAvailability(OVERRIDE_QUALITY_FACTOR_DECIDER) < 10000.0;
  }

  /**
   * Returns the override decider value.
   * @return
   */
  private double override() {
    return decider == null ? 1 : decider.getAvailability(OVERRIDE_QUALITY_FACTOR_DECIDER) / 10000.0;
  }

  /**
   * Returns true if the quality factor has changed since the last update.
   * @return
   */
  private boolean qualityFactorChanged() {
    return Math.abs(qualityFactor - previousQualityFactor) > 0.01;
  }

  /**
   * Returns true if we've entered a degraded state.
   * @return
   */
  private boolean degradationBegan() {
    return Math.abs(previousQualityFactor - 1.0) < 0.01 && qualityFactor < previousQualityFactor;
  }

  /**
   * Returns true if we've left the degraded state.
   * @return
   */
  private boolean degradationEnded() {
    return Math.abs(qualityFactor - 1.0) < 0.01 && previousQualityFactor < qualityFactor;
  }
}
