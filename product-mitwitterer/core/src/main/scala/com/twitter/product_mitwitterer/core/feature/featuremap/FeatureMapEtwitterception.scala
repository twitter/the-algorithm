package com.twitter.product_mitwitterer.core.feature.featuremap

import com.twitter.product_mitwitterer.core.feature.Feature

case class MissingFeatureEtwitterception(feature: Feature[_, _])
    etwittertends Etwitterception("Missing value for " + feature) {
  override def toString: String = getMessage
}

class InvalidPredictionRecordMergeEtwitterception
    etwittertends Etwitterception(
      "Use FeatureMap.plusPlusOptimized instead of FeatureMap.++ when the FeatureMaps on both sides of the merge contain PredictionRecords")
