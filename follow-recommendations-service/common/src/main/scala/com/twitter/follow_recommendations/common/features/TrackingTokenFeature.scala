package com.ExTwitter.follow_recommendations.common.features

import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

case object TrackingTokenFeature extends FeatureWithDefaultOnFailure[PipelineQuery, Option[Int]] {
  override val defaultValue: Option[Int] = None
}
