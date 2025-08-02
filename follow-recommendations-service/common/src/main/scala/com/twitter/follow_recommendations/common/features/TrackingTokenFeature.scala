package com.twitter.follow_recommendations.common.features

import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case object TrackingTokenFeature etwittertends FeatureWithDefaultOnFailure[PipelineQuery, Option[Int]] {
  override val defaultValue: Option[Int] = None
}
