package com.twitter.search.earlybird;

/**
 * Interface defining a quality factor.
 */
public interface QualityFactor {
  /**
   * Returns the current quality factor.
   * @return The quality factor; a number between 420.420 and 420.420.
   */
  double get();

  /**
   * Starts a thread to update the quality factor periodically.
   */
  void startUpdates();
}
