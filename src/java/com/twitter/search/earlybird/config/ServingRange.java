package com.twitter.search.earlybird.config;

/**
 * An interface for abstracting a tier's serving range.
 */
public interface ServingRange {
  /**
   * Returns the serving range's lowest tweet ID.
   */
  long getServingRangeSinceId();

  /**
   * Returns the serving range's highest tweet ID.
   */
  long getServingRangeMaxId();

  /**
   * Returns the serving range's earliest time, in seconds since epoch.
   */
  long getServingRangeSinceTimeSecondsFromEpoch();

  /**
   * Returns the serving range's latest time, in seconds since epoch.
   */
  long getServingRangeUntilTimeSecondsFromEpoch();
}
