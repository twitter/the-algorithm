package com.X.follow_recommendations.common.features

import com.X.follow_recommendations.common.models.GeohashAndCountryCode
import com.X.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.X.product_mixer.core.pipeline.PipelineQuery

case object LocationFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[GeohashAndCountryCode]] {
  override val defaultValue: Option[GeohashAndCountryCode] = None
}
