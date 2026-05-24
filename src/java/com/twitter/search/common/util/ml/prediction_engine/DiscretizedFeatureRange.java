package com.twitter.search.common.util.ml.prediction_engine;

import com.google.common.base.Preconditions;

/**
 * The discretized value range for a continous feature. After discretization a continuous feature
 * may become multiple discretized binary features, each occupying a range. This class stores this
 * range and a weight for it.
 */
public class DiscretizedFeatureRange {
  protected final double minValue;
  protected final double maxValue;
  protected final double weight;

  DiscretizedFeatureRange(double weight, String range) {
    String[] limits = range.split("_");
    Preconditions.checkArgument(limits.length == 2);

    this.minValue = parseRangeValue(limits[0]);
    this.maxValue = parseRangeValue(limits[1]);
    this.weight = weight;
  }

  private static double parseRangeValue(String value) {
    if ("inf".equals(value)) {
      return Double.POSITIVE_INFINITY;
    } else if ("-inf".equals(value)) {
      return Double.NEGATIVE_INFINITY;
    } else {
      return Double.parseDouble(value);
    }
  }
}
