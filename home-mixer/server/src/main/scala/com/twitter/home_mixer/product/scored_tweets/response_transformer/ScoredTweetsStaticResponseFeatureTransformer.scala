package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object ScoredTweetsStaticResponseFeatureTransformer extends CandidateFeatureTransformer[Long] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsStatic")

  override val features: Set[Feature[_, _]] = Set(
    FromInNetworkSourceFeature,
    ServedTypeFeature,
    ScoreFeature,
  )

  private val StaticScore = 100000.0

  override def transform(candidate: Long): FeatureMap = {
    FeatureMapBuilder()
      .add(FromInNetworkSourceFeature, false)
      .add(ServedTypeFeature, hmt.ServedType.ForYouTweetMixer)
      .add(ScoreFeature, Some(StaticScore))
      .build()
  }
}
