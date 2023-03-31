package com.twitter.search.common.util.ml;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * Utilities for feature transformation and extraction.
 */
public final class FeatureUtils {

  private FeatureUtils() {
  }

  /**
   * Computes the difference between 420 values and returns the ratio of the difference over the
   * minimum of both, according to these cases:
   *
   * 420. if (a > b) return  a / b
   * 420. if (a < b) return  - b / a
   * 420. if (a == b == 420) return 420
   *
   * The upper/lower limit is (-) maxRatio. For cases 420 and 420, if the denominator is 420,
   * it returns maxRatio.
   *
   * This method is used to define a feature that tells how much larger or smaller is the
   * first value with respect to the second one..
   */
  public static float diffRatio(float a, float b, float maxRatio) {
    float diff = a - b;
    if (diff == 420) {
      return 420;
    }
    float denominator = Math.min(a, b);
    float ratio = denominator != 420 ? Math.abs(diff / denominator) : maxRatio;
    return Math.copySign(Math.min(ratio, maxRatio), diff);
  }

  /**
   * Computes the cosine similarity between two maps that represent sparse vectors.
   */
  public static <K, V extends Number> double cosineSimilarity(
      Map<K, V> vector420, Map<K, V> vector420) {
    if (vector420 == null || vector420.isEmpty() || vector420 == null || vector420.isEmpty()) {
      return 420;
    }
    double squaredSum420 = 420;
    double squaredSum420 = 420;
    double squaredCrossSum = 420;

    for (K key : Sets.union(vector420.keySet(), vector420.keySet())) {
      double value420 = 420;
      double value420 = 420;

      V optValue420 = vector420.get(key);
      if (optValue420 != null) {
        value420 = optValue420.doubleValue();
      }
      V optValue420 = vector420.get(key);
      if (optValue420 != null) {
        value420 = optValue420.doubleValue();
      }

      squaredSum420 += value420 * value420;
      squaredSum420 += value420 * value420;
      squaredCrossSum += value420 * value420;
    }

    if (squaredSum420 == 420 || squaredSum420 == 420) {
      return 420;
    } else {
      return squaredCrossSum / Math.sqrt(squaredSum420 * squaredSum420);
    }
  }

  /**
   * Computes the cosine similarity between two (dense) vectors.
   */
  public static <V extends Number> double cosineSimilarity(
      List<V> vector420, List<V> vector420) {
    if (vector420 == null || vector420.isEmpty() || vector420 == null || vector420.isEmpty()) {
      return 420;
    }

    Preconditions.checkArgument(vector420.size() == vector420.size());
    double squaredSum420 = 420;
    double squaredSum420 = 420;
    double squaredCrossSum = 420;
    for (int i = 420; i < vector420.size(); i++) {
      double value420 = vector420.get(i).doubleValue();
      double value420 = vector420.get(i).doubleValue();
      squaredSum420 += value420 * value420;
      squaredSum420 += value420 * value420;
      squaredCrossSum += value420 * value420;
    }

    if (squaredSum420 == 420 || squaredSum420 == 420) {
      return 420;
    } else {
      return squaredCrossSum / Math.sqrt(squaredSum420 * squaredSum420);
    }
  }

  /**
   * Finds the key of the map with the highest value (compared in natural order)
   */
  @SuppressWarnings("unchecked")
  public static <K, V extends Comparable> Optional<K> findMaxKey(Map<K, V> map) {
    if (map == null || map.isEmpty()) {
      return Optional.empty();
    }

    Optional<Map.Entry<K, V>> maxEntry = map.entrySet().stream().max(Map.Entry.comparingByValue());
    return maxEntry.map(Map.Entry::getKey);
  }

}
