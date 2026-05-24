package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.ListIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mixer.product.scored_tweets.candidate_source.ListTweet
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object ScoredTweetsListsResponseFeatureTransformer extends CandidateFeatureTransformer[ListTweet] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsListsResponse")

  override val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    FromInNetworkSourceFeature,
    IsRetweetFeature,
    ServedTypeFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    ListIdFeature
  )

  override def transform(candidate: ListTweet): FeatureMap = FeatureMapBuilder()
    .add(AuthorIdFeature, candidate.tweet.userId)
    .add(FromInNetworkSourceFeature, false)
    .add(IsRetweetFeature, candidate.tweet.sourceStatusId.isDefined)
    .add(ServedTypeFeature, hmt.ServedType.ForYouList)
    .add(SourceTweetIdFeature, candidate.tweet.sourceStatusId)
    .add(SourceUserIdFeature, candidate.tweet.sourceUserId)
    .add(ListIdFeature, Some(candidate.listId))
    .build()
}
