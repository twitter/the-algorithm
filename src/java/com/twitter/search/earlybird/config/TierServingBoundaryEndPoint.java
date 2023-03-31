package com.twitter.search.earlybird.config;

import java.util.Date;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import com.twitter.common.util.Clock;
import com.twitter.search.common.partitioning.snowflakeparser.SnowflakeIdParser;

/**
 * The start or end boundary of a tier's serving range.
 * This is used to add since_id and max_id operators onto search queries.
 */
public class TierServingBoundaryEndPoint {
  @VisibleForTesting
  public static final String INFERRED_FROM_DATA_RANGE = "inferred_from_data_range";
  public static final String RELATIVE_TO_CURRENT_TIME_MS = "relative_to_current_time_ms";

  // Either offsetToCurrentTimeMillis is set or (absoluteTweetId and timeBoundarySecondsFromEpoch)
  // are set.
  @Nullable
  private final Long offsetToCurrentTimeMillis;
  @Nullable
  private final Long absoluteTweetId;
  @Nullable
  private final Long timeBoundarySecondsFromEpoch;
  private final Clock clock;

  TierServingBoundaryEndPoint(Long absoluteTweetId,
                              Long timeBoundarySecondsFromEpoch,
                              Long offsetToCurrentTimeMillis,
                              Clock clock) {
    this.offsetToCurrentTimeMillis = offsetToCurrentTimeMillis;
    this.absoluteTweetId = absoluteTweetId;
    this.timeBoundarySecondsFromEpoch = timeBoundarySecondsFromEpoch;
    this.clock = clock;
  }

  /**
   * Parse the boundary string and construct a TierServingBoundaryEndPoint instance.
   * @param boundaryString boundary configuration string. Valid values are:
   * <li>
   * "inferred_from_data_range" infers serving range from data range. This only works after
   *                               Nov 2010 when Twitter switched to snowflake IDs.
   *                               This is the default value.
   * </li>
   * <li>
   * "absolute_tweet_id_and_timestamp_millis:id:timestamp" a tweet ID/timestamp is given
   *                                                       explicitly as the serving range
   *                                                       boundary.
   * </li>
   * <li>
   * "relative_to_current_time_ms:offset" adds offset onto current timestamp in millis to
   *                                         compute serving range.
   * </li>
   *
   * @param boundaryDate the data boundary. This is used in conjunction with
   * inferred_from_data_date to determine the serving boundary.
   * @param clock  Clock used to obtain current time, when relative_to_current_time_ms is used.
   *               Tests pass in a FakeClock.
   */
  public static TierServingBoundaryEndPoint newTierServingBoundaryEndPoint(String boundaryString,
      Date boundaryDate,
      Clock clock) {
    if (boundaryString == null || boundaryString.trim().equals(
        INFERRED_FROM_DATA_RANGE)) {
      return inferBoundaryFromDataRange(boundaryDate, clock);
    } else if (boundaryString.trim().startsWith(RELATIVE_TO_CURRENT_TIME_MS)) {
      return getRelativeBoundary(boundaryString, clock);
    } else {
      throw new IllegalStateException("Cannot parse serving range string: " + boundaryString);
    }
  }

  private static TierServingBoundaryEndPoint inferBoundaryFromDataRange(Date boundaryDate,
                                                                        Clock clock) {
    // infer from data range
    // handle default start date and end date, in case the dates are not specified in the config
    if (boundaryDate.equals(TierConfig.DEFAULT_TIER_START_DATE)) {
      return new TierServingBoundaryEndPoint(
          -1L, TierConfig.DEFAULT_TIER_START_DATE.getTime() / 1000, null, clock);
    } else if (boundaryDate.equals(TierConfig.DEFAULT_TIER_END_DATE)) {
      return new TierServingBoundaryEndPoint(
          Long.MAX_VALUE, TierConfig.DEFAULT_TIER_END_DATE.getTime() / 1000, null, clock);
    } else {
      // convert data start / end dates into since / max ID.
      long boundaryTimeMillis = boundaryDate.getTime();
      if (!SnowflakeIdParser.isUsableSnowflakeTimestamp(boundaryTimeMillis)) {
        throw new IllegalStateException("Serving time range can not be determined, because "
            + boundaryDate + " is before Twitter switched to snowflake tweet IDs.");
      }
      // Earlybird since_id is inclusive and max_id is exclusive. We substract 1 here.
      // Consider example:
      //   full0:  5000 (inclusive) - 6000 (exclusive)
      //   full1:  6000 (inclusive) - 7000 (exclusive)
      // For tier full0, we should use max_id 5999 instead of 6000.
      // For tier full1, we should use since_id 5999 instead of 6000.
      // Hence we substract 1 here.
      long adjustedTweetId =
        SnowflakeIdParser.generateValidStatusId(boundaryTimeMillis, 0) - 1;
      Preconditions.checkState(adjustedTweetId >= 0, "boundary tweet ID must be non-negative");
      return new TierServingBoundaryEndPoint(
          adjustedTweetId, boundaryTimeMillis / 1000, null, clock);
    }
  }

  private static TierServingBoundaryEndPoint getRelativeBoundary(String boundaryString,
                                                                 Clock clock) {
    // An offset relative to current time is given
    String[] parts = boundaryString.split(":");
    Preconditions.checkState(parts.length == 2);
    long offset = Long.parseLong(parts[1]);
    return new TierServingBoundaryEndPoint(null, null, offset, clock);
  }

  /**
   * Returns the tweet ID for this tier boundary. If the tier boundary was created using a tweet ID,
   * that tweet ID is returned. Otherwise, a tweet ID is derived from the time boundary.
   */
  @VisibleForTesting
  public long getBoundaryTweetId() {
    // If absoluteTweetId is available, use it.
    if (absoluteTweetId != null) {
      return absoluteTweetId;
    } else {
      Preconditions.checkNotNull(offsetToCurrentTimeMillis);
      long boundaryTime = clock.nowMillis() + offsetToCurrentTimeMillis;
      return SnowflakeIdParser.generateValidStatusId(boundaryTime, 0);
    }
  }

  /**
   * Returns the time boundary for this tier boundary, in seconds since epoch.
   */
  public long getBoundaryTimeSecondsFromEpoch() {
    if (timeBoundarySecondsFromEpoch != null) {
      return timeBoundarySecondsFromEpoch;
    } else {
      Preconditions.checkNotNull(offsetToCurrentTimeMillis);
      return (clock.nowMillis() + offsetToCurrentTimeMillis) / 1000;
    }
  }
}
