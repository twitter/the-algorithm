package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineservice.{thriftscala => tlsthrift}
import com.twitter.tweet_mixer.feature.FromInNetworkSourceFeature
import com.twitter.tweet_mixer.feature.ScoreFeature
import com.twitter.tweet_mixer.feature.SourceSignalFeature

object TweetFeatureTimelineServiceTransformer extends CandidateFeatureTransformer[tlsthrift.Tweet] {
  override def features: Set[Feature[_, _]] =
    Set(ScoreFeature, SourceSignalFeature, FromInNetworkSourceFeature)

  override val identifier: TransformerIdentifier = TransformerIdentifier(
    "TweetFeatureTimelineService")

  override def transform(
    input: tlsthrift.Tweet
  ): FeatureMap =
    FeatureMap(
      SourceSignalFeature,
      input._1,
      ScoreFeature,
      1.0,
      FromInNetworkSourceFeature,
      false
    )
}
