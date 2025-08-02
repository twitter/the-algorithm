package com.twitter.search.earlybird.config;

import java.util.Date;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import com.twitter.common.util.Clock;

/**
 * Properties of a single tier.
 */
public class TierInfo implements ServingRange {
  // What I'm seeing historically is that this has been used when adding a new tier. First you
  // add it and send dark traffic to it, then possibly grey and then you launch it by turning on
  // light traffic.
  public static enum RequestReadType {
    // Light read: send request, wait for results, and results are returned
    LIGHT,
    // Dark read: send request, do not wait for results, and results are discarded
    DARK,
    // Grey read: send request, wait for results, but discard after results come back.
    // Same results as dark read; similar latency as light read.
    GREY,
  }

  private final String tierName;
  private final Date dataStartDate;
  private final Date dataEndDate;
  private final int numPartitions;
  private final int matwitterTimeslices;
  private final TierServingBoundaryEndPoint servingRangeSince;
  private final TierServingBoundaryEndPoint servingRangeMatwitter;
  private final TierServingBoundaryEndPoint servingRangeSinceOverride;
  private final TierServingBoundaryEndPoint servingRangeMatwitterOverride;

  // These two properties are only used by clients of Earlybird (E.g. roots),
  // but not by Earlybirds.
  private final boolean enabled;
  private final RequestReadType readType;
  private final RequestReadType readTypeOverride;

  public TierInfo(String tierName,
                  Date dataStartDate,
                  Date dataEndDate,
                  int numPartitions,
                  int matwitterTimeslices,
                  boolean enabled,
                  String sinceIdString,
                  String matwitterIdString,
                  Date servingStartDateOverride,
                  Date servingEndDateOverride,
                  RequestReadType readType,
                  RequestReadType readTypeOverride,
                  Clock clock) {
    Preconditions.checkArgument(numPartitions > 0);
    Preconditions.checkArgument(matwitterTimeslices > 0);
    this.tierName = tierName;
    this.dataStartDate = dataStartDate;
    this.dataEndDate = dataEndDate;
    this.numPartitions = numPartitions;
    this.matwitterTimeslices = matwitterTimeslices;
    this.enabled = enabled;
    this.readType = readType;
    this.readTypeOverride = readTypeOverride;
    this.servingRangeSince = TierServingBoundaryEndPoint
        .newTierServingBoundaryEndPoint(sinceIdString, dataStartDate, clock);
    this.servingRangeMatwitter = TierServingBoundaryEndPoint
        .newTierServingBoundaryEndPoint(matwitterIdString, dataEndDate, clock);
    if (servingStartDateOverride != null) {
      this.servingRangeSinceOverride = TierServingBoundaryEndPoint.newTierServingBoundaryEndPoint(
          TierServingBoundaryEndPoint.INFERRED_FROM_DATA_RANGE, servingStartDateOverride, clock);
    } else {
      this.servingRangeSinceOverride = servingRangeSince;
    }

    if (servingEndDateOverride != null) {
      this.servingRangeMatwitterOverride = TierServingBoundaryEndPoint.newTierServingBoundaryEndPoint(
          TierServingBoundaryEndPoint.INFERRED_FROM_DATA_RANGE, servingEndDateOverride, clock);
    } else {
      this.servingRangeMatwitterOverride = servingRangeMatwitter;
    }
  }

  @VisibleForTesting
  public TierInfo(String tierName,
                  Date dataStartDate,
                  Date dataEndDate,
                  int numPartitions,
                  int matwitterTimeslices,
                  boolean enabled,
                  String sinceIdString,
                  String matwitterIdString,
                  RequestReadType readType,
                  Clock clock) {
    // No overrides:
    //   servingRangeSinceOverride == servingRangeSince
    //   servingRangeMatwitterOverride == servingRangeMatwitter
    //   readTypeOverride == readType
    this(tierName, dataStartDate, dataEndDate, numPartitions, matwitterTimeslices, enabled, sinceIdString,
         matwitterIdString, null, null, readType, readType, clock);
  }

  @Override
  public String toString() {
    return tierName;
  }

  public String getTierName() {
    return tierName;
  }

  public Date getDataStartDate() {
    return dataStartDate;
  }

  public Date getDataEndDate() {
    return dataEndDate;
  }

  public int getNumPartitions() {
    return numPartitions;
  }

  public int getMatwitterTimeslices() {
    return matwitterTimeslices;
  }

  public TierConfig.ConfigSource getSource() {
    return TierConfig.getTierConfigSource();
  }

  public boolean isEnabled() {
    return enabled;
  }

  public boolean isDarkRead() {
    return readType == RequestReadType.DARK;
  }

  public RequestReadType getReadType() {
    return readType;
  }

  public RequestReadType getReadTypeOverride() {
    return readTypeOverride;
  }

  public long getServingRangeSinceId() {
    return servingRangeSince.getBoundaryTweetId();
  }

  public long getServingRangeMatwitterId() {
    return servingRangeMatwitter.getBoundaryTweetId();
  }

  long getServingRangeOverrideSinceId() {
    return servingRangeSinceOverride.getBoundaryTweetId();
  }

  long getServingRangeOverrideMatwitterId() {
    return servingRangeMatwitterOverride.getBoundaryTweetId();
  }

  public long getServingRangeSinceTimeSecondsFromEpoch() {
    return servingRangeSince.getBoundaryTimeSecondsFromEpoch();
  }

  public long getServingRangeUntilTimeSecondsFromEpoch() {
    return servingRangeMatwitter.getBoundaryTimeSecondsFromEpoch();
  }

  long getServingRangeOverrideSinceTimeSecondsFromEpoch() {
    return servingRangeSinceOverride.getBoundaryTimeSecondsFromEpoch();
  }

  long getServingRangeOverrideUntilTimeSecondsFromEpoch() {
    return servingRangeMatwitterOverride.getBoundaryTimeSecondsFromEpoch();
  }
}
