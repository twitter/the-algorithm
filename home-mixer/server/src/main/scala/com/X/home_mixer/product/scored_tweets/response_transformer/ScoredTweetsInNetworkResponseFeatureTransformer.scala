package com.X.home_mixer.product.scored_tweets.response_transformer

import com.X.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.X.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.timelineranker.{thriftscala => tlr}
import com.X.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.X.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsInNetworkResponseFeatureTransformer
    extends CandidateFeatureTransformer[tlr.CandidateTweet] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsInNetworkResponse")

  override val features: Set[Feature[_, _]] = TimelineRankerResponseTransformer.features

  override def transform(candidate: tlr.CandidateTweet): FeatureMap = {
    val baseFeatures = TimelineRankerResponseTransformer.transform(candidate)

    val features = FeatureMapBuilder()
      .add(CandidateSourceIdFeature, Some(cts.CandidateTweetSourceId.RecycledTweet))
      .add(FromInNetworkSourceFeature, true)
      .add(SuggestTypeFeature, Some(st.SuggestType.RankedTimelineTweet))
      .build()

    baseFeatures ++ features
  }
}
