package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object ScoredVideoTweetsPinnedTweetResponseFeatureTransformer
    extends CandidateFeatureTransformer[TweetCandidate] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredVideoTweetsPinnedTweetResponse")

  override val features: Set[Feature[_, _]] = Set(
    ServedTypeFeature
  )

  override def transform(candidate: TweetCandidate): FeatureMap = FeatureMapBuilder()
    .add(ServedTypeFeature, hmt.ServedType.ForYouPinned)
    .build()

}
