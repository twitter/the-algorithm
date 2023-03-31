package com.twitter.search.common.util.ml;

import java.util.Map;

/**
 * An interface for linear models that are backed by some sort of map
 */
public interface MapBasedLinearModel<K> {
  /**
   * Evaluate using this model given a feature vector.
   * @param instance The feature vector in format of a hashmap.
   * @return
   */
  boolean classify(Map<K, Float> instance);

  /**
   * Evaluate using this model given a classification threshold and a feature vector.
   * @param threshold Score threshold used for classification.
   * @param instance The feature vector in format of a hashmap.
   * @return
   */
  boolean classify(float threshold, Map<K, Float> instance);

  /**
   * Computes the score of an instance as a linear combination of the features and the model
   * weights. 0 is used as default value for features or weights that are not present.
   *
   * @param instance The feature vector in format of a hashmap.
   * @return The instance score according to the model.
   */
  float score(Map<K, Float> instance);
}
