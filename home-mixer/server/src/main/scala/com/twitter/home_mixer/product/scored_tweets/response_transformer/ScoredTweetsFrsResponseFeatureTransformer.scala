package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineranker.{thriftscala => tlr}

object ScoredTweetsFrsResponseFeatureTransformer
    extends CandidateFeatureTransformer[tlr.CandidateTweet] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("ScoredTweetsFrsResponse")

  override val features: Set[Feature[_, _]] = TimelineRankerResponseTransformer.features

  override def transform(candidate: tlr.CandidateTweet): FeatureMap = {
    val baseFeatures = TimelineRankerResponseTransformer.transform(candidate)

    val features = FeatureMapBuilder()
      .add(ServedTypeFeature, hmt.ServedType.ForYouFrs)
      .build()

    baseFeatures ++ features
  }
}
