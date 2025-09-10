package com.twitter.home_mixer.product.for_you.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object ExplorationTweetResponseFeatureTransformer extends CandidateFeatureTransformer[Long] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ExplorationTweetResponse")

  override val features: Set[Feature[_, _]] =
    Set(ServedTypeFeature)

  def transform(
    input: Long
  ): FeatureMap = {
    FeatureMapBuilder()
      .add(ServedTypeFeature, hmt.ServedType.ForYouExploration)
      .build()
  }
}
