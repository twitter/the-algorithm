package com.X.home_mixer.product.scored_tweets.response_transformer

import com.X.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.X.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.X.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsBackfillResponseFeatureTransformer extends CandidateFeatureTransformer[Long] {

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
