package com.ExTwitter.home_mixer.product.scored_tweets.response_transformer

import com.ExTwitter.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.ExTwitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.ExTwitter.timelineservice.suggests.{thriftscala => st}

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
