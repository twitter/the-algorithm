package com.ExTwitter.follow_recommendations.common.features

import com.ExTwitter.follow_recommendations.common.models.GeohashAndCountryCode
import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

case object LocationFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, Option[GeohashAndCountryCode]] {
  override val defaultValue: Option[GeohashAndCountryCode] = None
}
