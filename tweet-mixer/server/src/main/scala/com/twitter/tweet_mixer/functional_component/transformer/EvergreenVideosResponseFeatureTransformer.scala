package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.tweet_mixer.feature.FromInNetworkSourceFeature
import com.twitter.tweet_mixer.feature.ScoreFeature
import com.twitter.tweet_mixer.feature.SourceSignalFeature
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate

object EvergreenVideosResponseFeatureTransformer
    extends CandidateFeatureTransformer[TweetMixerCandidate] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("EvergreenVideos")

  override def features: Set[Feature[_, _]] =
    Set(SourceSignalFeature, FromInNetworkSourceFeature, ScoreFeature)

  override def transform(input: TweetMixerCandidate): FeatureMap =
    FeatureMap(
      SourceSignalFeature,
      input.seedId,
      FromInNetworkSourceFeature,
      false,
      ScoreFeature,
      input.score
    )
}
