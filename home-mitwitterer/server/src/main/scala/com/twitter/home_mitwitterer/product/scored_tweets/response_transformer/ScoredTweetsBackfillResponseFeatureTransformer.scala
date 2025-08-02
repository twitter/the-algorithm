package com.twitter.home_mitwitterer.product.scored_tweets.response_transformer

import com.twitter.home_mitwitterer.model.HomeFeatures.CandidateSourceIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.twitter.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsBackfillResponseFeatureTransformer etwittertends CandidateFeatureTransformer[Long] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsBackfillResponse")

  override val features: Set[Feature[_, _]] = Set(
    CandidateSourceIdFeature,
    FromInNetworkSourceFeature,
    SuggestTypeFeature
  )

  override def transform(candidate: Long): FeatureMap = FeatureMapBuilder()
    .add(CandidateSourceIdFeature, Some(cts.CandidateTweetSourceId.BackfillOrganicTweet))
    .add(FromInNetworkSourceFeature, true)
    .add(SuggestTypeFeature, Some(st.SuggestType.RankedOrganicTweet))
    .build()
}
