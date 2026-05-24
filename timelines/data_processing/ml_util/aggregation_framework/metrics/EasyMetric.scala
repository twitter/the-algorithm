package com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics

import com.twitter.ml.api._

/**
 * A "human-readable" metric that can be applied to features of multiple
 * different types. Wrapper around AggregationMetric used as syntactic sugar
 * for easier config.
 */
trait EasyMetric extends Serializable {
  /*
   * Given a feature type, fetches the corrrect underlying AggregationMetric
   * to perform this operation over the given feature type, if any. If no such
   * metric is available, returns None. For example, MEAN cannot be applied
   * to FeatureType.String and would return None.
   *
   * @param featureType Type of feature to fetch metric for
   * @param useFixedDecay Param to control whether the metric should use fixed decay
   *   logic (if appropriate)
   * @return Strongly typed aggregation metric to use for this feature type
   *
   * For example, if the EasyMetric is MEAN and the featureType is
   * FeatureType.Continuous, the underlying AggregationMetric should be a
   * scalar mean. If the EasyMetric is MEAN and the featureType is
   * FeatureType.SparseContinuous, the AggregationMetric returned could be a
   * "vector" mean that averages sparse maps. Using the single logical name
   * MEAN for both is nice syntactic sugar making for an easier to read top
   * level config, though different underlying operators are used underneath
   * for the actual implementation.
   */
  def forFeatureType[T](
    featureType: FeatureType,
  ): Option[AggregationMetric[T, _]]
}
