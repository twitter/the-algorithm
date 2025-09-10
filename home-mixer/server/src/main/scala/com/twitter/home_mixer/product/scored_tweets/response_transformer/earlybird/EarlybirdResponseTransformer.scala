package com.twitter.home_mixer.product.scored_tweets.response_transformer.earlybird

import com.twitter.home_mixer.model.HomeFeatures.EarlybirdScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdSearchResultFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.search.earlybird.{thriftscala => eb}

object EarlybirdResponseTransformer {

  val features: Set[Feature[_, _]] = Set(
    EarlybirdScoreFeature,
    EarlybirdSearchResultFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsRetweetFeature,
    TweetUrlsFeature
  )

  def transform(candidate: eb.ThriftSearchResult): FeatureMap = {
    val metadata = candidate.metadata
    val isRetweet = metadata.flatMap(_.isRetweet).getOrElse(false)
    val sharedStatusId = metadata.map(_.sharedStatusId).getOrElse(0L)
    val referencedTweetAuthorId = metadata.map(_.referencedTweetAuthorId).getOrElse(0L)
    val inReplyToTweetId = if (!isRetweet && sharedStatusId > 0) Some(sharedStatusId) else None
    val inReplyToUserId =
      if (!isRetweet && sharedStatusId > 0 && referencedTweetAuthorId > 0)
        Some(referencedTweetAuthorId)
      else None

    FeatureMapBuilder()
      .add(EarlybirdSearchResultFeature, Some(candidate))
      .add(EarlybirdScoreFeature, candidate.metadata.flatMap(_.score))
      .add(InReplyToTweetIdFeature, inReplyToTweetId)
      .add(InReplyToUserIdFeature, inReplyToUserId)
      .add(IsRetweetFeature, isRetweet)
      .add(
        TweetUrlsFeature,
        candidate.metadata.flatMap(_.tweetUrls.map(_.map(_.originalUrl))).getOrElse(Seq.empty))
      .build()
  }
}
