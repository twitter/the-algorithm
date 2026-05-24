package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.tweet_mixer.feature.TopicTweetScore
import com.twitter.tweet_mixer.feature.TweetTopicIdFeature
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate

object TopicTweetFeatureTransformer extends CandidateFeatureTransformer[TweetMixerCandidate] {
  override def features: Set[Feature[_, _]] = Set(TopicTweetScore, TweetTopicIdFeature)

  override val identifier: TransformerIdentifier = TransformerIdentifier(
    "TopicTweetFeatureTransformer")

  override def transform(candidate: TweetMixerCandidate): FeatureMap =
    FeatureMapBuilder()
      .add(TopicTweetScore, Some(candidate.score))
      .add(TweetTopicIdFeature, candidate.seedId)
      .build()
}
