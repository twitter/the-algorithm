package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.tweet_mixer.feature.TripTweetScore
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripTweet

object TripTweetFeatureTransformer extends CandidateFeatureTransformer[TripTweet] {
  override def features: Set[Feature[_, _]] = Set(TripTweetScore)

  override val identifier: TransformerIdentifier = TransformerIdentifier(
    "TripTweetCandidateFeature")

  override def transform(input: TripTweet): FeatureMap =
    FeatureMapBuilder().add(TripTweetScore, Some(input.score)).build()
}
