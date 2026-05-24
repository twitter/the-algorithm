package com.twitter.search.common.relevance.features;

import com.google.common.base.Preconditions;

/**
 * Scoring utilities
 */
public final class ScoringUtils {
  private ScoringUtils() { }

  /**
   * normalize a positive value of arbitrary range to [0.0, 1.0], with a slop
   * @param value the value to normalize.
   * @param halfval a reference value that will be normalized to 0.5
   * @param exp an exponential parameter (must be positive) to control the converging speed,
   * the smaller the value the faster it reaches the halfval but slower it reaches the maximum.
   * @return a normalized value
   */
  public static float normalize(float value, double halfval, double exp) {
    Preconditions.checkArgument(exp > 0.0 && exp <= 1.0);
    return (float) (Math.pow(value, exp) / (Math.pow(value, exp) + Math.pow(halfval, exp)));
  }

}
