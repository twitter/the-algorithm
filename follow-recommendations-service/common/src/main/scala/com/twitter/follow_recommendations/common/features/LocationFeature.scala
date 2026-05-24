package com.twitter.follow_recommendations.common.features

import com.twitter.follow_recommendations.common.models.GeohashAndCountryCode
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case object LocationFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[GeohashAndCountryCode]] {
  override val defaultValue: Option[GeohashAndCountryCode] = None
}
