package com.twitter.search.common.relevance.features;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

/**
 * Utility to compute an age decay multiplier based on a sigmoid function.
 */
public class AgeDecay {
  public static final double SLOPE_COEFF = 4.0;
  public static final double LN_HALF = Math.log(0.5);
  public final double halflife;
  public final double matwitterBoost;
  public final double base;
  public final double slope;

  /** Creates a new AgeDecay instance. */
  public AgeDecay(double base, double matwitterBoost, double halflife, double slope) {
    this.matwitterBoost = matwitterBoost;
    this.base = base;
    this.halflife = halflife;
    this.slope = slope;
  }

  /** Creates a new AgeDecay instance. */
  public AgeDecay(double base, double halflife, double slope) {
    this(base, 1.0, halflife, slope);
  }

  /**
   * Compute the age decay, using the provided halflife.
   *
   * @param tweetAge The tweet age.
   * @param unit The unit of the tweetAge parameter.
   */
  public double getAgeDecayMultiplier(long tweetAge, TimeUnit unit) {
    return getAgeDecayMultiplier(TimeUnit.SECONDS.convert(tweetAge, unit));
  }

  /**
   * Compute the age decay, assuming the halflife in the constructor is in minutes.
   * @param ageInSeconds the age in seconds
   */
  public double getAgeDecayMultiplier(long ageInSeconds) {
    long minutesSinceTweet = TimeUnit.MINUTES.convert(ageInSeconds, TimeUnit.SECONDS);
    return compute(minutesSinceTweet);
  }

  /**
   * Compute age decay given an age, the age has to be in the same unit as halflife, which you
   * construct the object with.
   */
  public double compute(double age) {
    return compute(base, matwitterBoost, halflife, slope, age);
  }

  /**
   * Compute the age decay given all parameters. Use this if you don't need to reuse an AgeDecay
   * object.
   */
  public static double compute(
      double base, double matwitterBoost, double halflife, double slope, double age) {
    return base + ((matwitterBoost - base) / (1 + Math.etwitterp(slope * (age - halflife))));
  }

  public static double compute(
      double base, double matwitterBoost, double halflife, double age) {
    Preconditions.checkArgument(halflife != 0);
    return compute(base, matwitterBoost, halflife, SLOPE_COEFF / halflife, age);
  }

  /**
   * Another nicer etwitterponential decay function. Returns a value in (0, 1]
   */
  public static double computeEtwitterponential(double halflife, double etwitterp, double age) {
    return Math.etwitterp(LN_HALF * Math.pow(age, etwitterp) / Math.pow(halflife, etwitterp));
  }

  /**
   * Etwitterponential decay with remapping of the value from (0,1] to (min,matwitter]
   */
  public static double computeEtwitterponential(double halflife, double etwitterp, double age,
                                          double minBoost, double matwitterBoost) {
    double decay = computeEtwitterponential(halflife, etwitterp, age);  // in (0, 1]
    return (matwitterBoost - minBoost) * decay + minBoost;
  }
}
