package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceSignalFeature
import com.twitter.home_mixer.model.candidate_source.SourceSignal
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineranker.{thriftscala => tlr}

object ScoredTweetsInNetworkResponseFeatureTransformer
    extends CandidateFeatureTransformer[tlr.CandidateTweet] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsInNetworkResponse")

  override val features: Set[Feature[_, _]] =
    TimelineRankerResponseTransformer.features ++ Set(SourceSignalFeature)

  override def transform(candidate: tlr.CandidateTweet): FeatureMap = {
    val baseFeatures = TimelineRankerResponseTransformer.transform(candidate)

    val features = FeatureMapBuilder()
      .add(FromInNetworkSourceFeature, true)
      .add(ServedTypeFeature, hmt.ServedType.ForYouInNetwork)
      .add(
        SourceSignalFeature,
        Some(
          SourceSignal(
            id = candidate.tweet.flatMap(_.coreData.map(_.userId)).getOrElse(0L),
            signalType = None,
            signalEntity = None,
            authorId = None,
          ))
      )
      .build()

    baseFeatures ++ features
  }
}
