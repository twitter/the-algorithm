package com.twitter.search.earlybird_root.config;

import java.util.Date;

import com.twitter.common.util.Clock;
import com.twitter.search.earlybird.config.ServingRange;
import com.twitter.search.earlybird.config.TierServingBoundaryEndPoint;

/**
 * Time boundary information for a root cluster.
 * Used by EarlybirdTimeRangeFilter.
 */
public class RootClusterBoundaryInfo implements ServingRange {

  private final TierServingBoundaryEndPoint servingRangeSince;
  private final TierServingBoundaryEndPoint servingRangeMax;

  /**
   * Build a time boundary information
   */
  public RootClusterBoundaryInfo(
      Date startDate,
      Date clusterEndDate,
      String sinceIdBoundaryString,
      String maxIdBoundaryString,
      Clock clock) {
    this.servingRangeSince = TierServingBoundaryEndPoint
        .newTierServingBoundaryEndPoint(sinceIdBoundaryString, startDate, clock);
    this.servingRangeMax = TierServingBoundaryEndPoint
        .newTierServingBoundaryEndPoint(maxIdBoundaryString, clusterEndDate, clock);
  }

  public long getServingRangeSinceId() {
    return servingRangeSince.getBoundaryTweetId();
  }

  public long getServingRangeMaxId() {
    return servingRangeMax.getBoundaryTweetId();
  }

  public long getServingRangeSinceTimeSecondsFromEpoch() {
    return servingRangeSince.getBoundaryTimeSecondsFromEpoch();
  }

  public long getServingRangeUntilTimeSecondsFromEpoch() {
    return servingRangeMax.getBoundaryTimeSecondsFromEpoch();
  }
}
