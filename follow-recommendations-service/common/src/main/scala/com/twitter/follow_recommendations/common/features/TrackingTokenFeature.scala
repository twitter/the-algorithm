package com.twitter.follow_recommendations.common.features

import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case object TrackingTokenFeature extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Int]] {
  override val defaultValue: Option[Int] = None
}
