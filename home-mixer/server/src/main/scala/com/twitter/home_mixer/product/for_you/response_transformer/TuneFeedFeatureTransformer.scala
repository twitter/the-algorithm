package com.twitter.home_mixer.product.for_you.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object TuneFeedFeatureTransformer extends CandidateFeatureTransformer[TweetCandidate] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("TuneFeed")

  override val features: Set[Feature[_, _]] = Set(ServedTypeFeature)

  def transform(
    input: TweetCandidate
  ): FeatureMap = FeatureMap(ServedTypeFeature, hmt.ServedType.ForYouPopularTopic)
}
