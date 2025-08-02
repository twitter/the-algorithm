package com.twitter.follow_recommendations.common.features

import com.twitter.follow_recommendations.common.models.GeohashAndCountryCode
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case object LocationFeature
    etwittertends FeatureWithDefaultOnFailure[PipelineQuery, Option[GeohashAndCountryCode]] {
  override val defaultValue: Option[GeohashAndCountryCode] = None
}
