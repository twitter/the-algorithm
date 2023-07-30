package com.X.home_mixer.product.scored_tweets.response_transformer

import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.X.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.X.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.X.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.timelineservice.{thriftscala => t}
import com.X.timelineservice.suggests.{thriftscala => st}
import com.X.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}

object ScoredTweetsListsResponseFeatureTransformer extends CandidateFeatureTransformer[t.Tweet] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsListsResponse")

  override val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    CandidateSourceIdFeature,
    FromInNetworkSourceFeature,
    IsRetweetFeature,
    SuggestTypeFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
  )

  override def transform(candidate: t.Tweet): FeatureMap = {
    FeatureMapBuilder()
      .add(AuthorIdFeature, candidate.userId)
      .add(CandidateSourceIdFeature, Some(cts.CandidateTweetSourceId.ListTweet))
      .add(FromInNetworkSourceFeature, false)
      .add(IsRetweetFeature, candidate.sourceStatusId.isDefined)
      .add(SuggestTypeFeature, Some(st.SuggestType.RankedListTweet))
      .add(SourceTweetIdFeature, candidate.sourceStatusId)
      .add(SourceUserIdFeature, candidate.sourceUserId)
      .build()
  }
}
