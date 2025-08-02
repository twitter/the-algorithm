package com.twitter.home_mitwitterer.product.scored_tweets.response_transformer

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.CandidateSourceIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineservice.{thriftscala => t}
import com.twitter.timelineservice.suggests.{thriftscala => st}
import com.twitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}

object ScoredTweetsListsResponseFeatureTransformer etwittertends CandidateFeatureTransformer[t.Tweet] {

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
