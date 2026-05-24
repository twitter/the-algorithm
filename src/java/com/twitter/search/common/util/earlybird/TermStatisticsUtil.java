package com.twitter.search.common.util.earlybird;

import java.util.concurrent.TimeUnit;

import com.twitter.search.earlybird.thrift.ThriftHistogramSettings;

/**
 * A utility class to provide some functions for TermStatistics request processing
 */
public final class TermStatisticsUtil {

  private static final org.slf4j.Logger LOG =
      org.slf4j.LoggerFactory.getLogger(TermStatisticsUtil.class);

  private TermStatisticsUtil() {
  }

  /**
   * Determine the binsize base on settings in ThriftHistogramSettings.granularity
   */
  public static int determineBinSize(ThriftHistogramSettings histogramSettings) {
    final int DEFAULT_BINSIZE = (int) TimeUnit.HOURS.toSeconds(1);
    int binSize;
    switch (histogramSettings.getGranularity()) {
      case DAYS:
        binSize = (int) TimeUnit.DAYS.toSeconds(1);
        break;
      case HOURS:
        binSize = (int) TimeUnit.HOURS.toSeconds(1);
        break;
      case MINUTES:
        binSize = (int) TimeUnit.MINUTES.toSeconds(1);
        break;
      case CUSTOM:
        binSize = histogramSettings.isSetBinSizeInSeconds()
                      ? histogramSettings.getBinSizeInSeconds()
                      : DEFAULT_BINSIZE;
        break;
      default:
        binSize = DEFAULT_BINSIZE;
        LOG.warn("Unknown ThriftHistogramGranularityType {} using default binsize: {}",
                 histogramSettings.getGranularity(), DEFAULT_BINSIZE);
    }

    return binSize;
  }
}
