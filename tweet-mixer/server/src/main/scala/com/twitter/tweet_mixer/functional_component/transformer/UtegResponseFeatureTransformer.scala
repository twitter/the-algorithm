package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.recos.user_tweet_entity_graph.{thriftscala => uteg}
import com.twitter.tweet_mixer.feature.FromInNetworkSourceFeature
import com.twitter.tweet_mixer.feature.ScoreFeature
import com.twitter.tweet_mixer.feature.SourceSignalFeature

object UtegResponseFeatureTransformer
    extends CandidateFeatureTransformer[uteg.TweetRecommendation] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("UtegResponse")

  override def features: Set[Feature[_, _]] =
    Set(ScoreFeature, SourceSignalFeature, FromInNetworkSourceFeature)

  override def transform(input: uteg.TweetRecommendation): FeatureMap =
    FeatureMap(
      ScoreFeature,
      input.score,
      SourceSignalFeature,
      -1L,
      FromInNetworkSourceFeature,
      false
    )
}
