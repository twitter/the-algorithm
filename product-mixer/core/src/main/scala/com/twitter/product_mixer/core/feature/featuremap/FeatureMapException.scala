package com.twitter.product_mixer.core.feature.featuremap

import com.twitter.product_mixer.core.feature.Feature

case class MissingFeatureException(feature: Feature[_, _])
    extends Exception("Missing value for " + feature) {
  override def toString: String = getMessage
}

class InvalidPredictionRecordMergeException
    extends Exception(
      "Use FeatureMap.plusPlusOptimized instead of FeatureMap.++ when the FeatureMaps on both sides of the merge contain PredictionRecords")
