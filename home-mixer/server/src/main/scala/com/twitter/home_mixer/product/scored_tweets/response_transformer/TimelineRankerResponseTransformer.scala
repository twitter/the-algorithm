package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.DirectedAtUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.HasImageFeature
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.MentionScreenNameFeature
import com.twitter.home_mixer.model.HomeFeatures.MentionUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.home_mixer.util.tweetypie.content.TweetMediaFeaturesExtractor
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.timelineranker.{thriftscala => tlr}

object TimelineRankerResponseTransformer {

  val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    CommunityIdFeature,
    DirectedAtUserIdFeature,
    EarlybirdFeature,
    EarlybirdScoreFeature,
    ExclusiveConversationAuthorIdFeature,
    FromInNetworkSourceFeature,
    HasImageFeature,
    HasVideoFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsRetweetFeature,
    MentionScreenNameFeature,
    MentionUserIdFeature,
    QuotedTweetIdFeature,
    QuotedUserIdFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    ServedTypeFeature,
    TweetUrlsFeature
  )

  def transform(candidate: tlr.CandidateTweet): FeatureMap = {
    val tweet = candidate.tweet
    val quotedTweet = tweet.filter(_.quotedTweet.exists(_.tweetId != 0)).flatMap(_.quotedTweet)
    val mentions = tweet.flatMap(_.mentions).getOrElse(Seq.empty)
    val coreData = tweet.flatMap(_.coreData)
    val share = coreData.flatMap(_.share)
    val reply = coreData.flatMap(_.reply)
    val communityId = tweet.flatMap(_.communities).flatMap(_.communityIds.headOption)

    FeatureMapBuilder()
      .add(AuthorIdFeature, coreData.map(_.userId))
      .add(CommunityIdFeature, communityId)
      .add(DirectedAtUserIdFeature, coreData.flatMap(_.directedAtUser.map(_.userId)))
      .add(EarlybirdFeature, candidate.features)
      .add(EarlybirdScoreFeature, candidate.features.map(_.earlybirdScore))
      .add(
        ExclusiveConversationAuthorIdFeature,
        tweet.flatMap(_.exclusiveTweetControl.map(_.conversationAuthorId)))
      .add(FromInNetworkSourceFeature, false)
      .add(HasImageFeature, tweet.exists(TweetMediaFeaturesExtractor.hasImage))
      .add(HasVideoFeature, tweet.exists(TweetMediaFeaturesExtractor.hasVideo))
      .add(InReplyToTweetIdFeature, reply.flatMap(_.inReplyToStatusId))
      .add(InReplyToUserIdFeature, reply.map(_.inReplyToUserId))
      .add(IsRetweetFeature, share.isDefined)
      .add(MentionScreenNameFeature, mentions.map(_.screenName))
      .add(MentionUserIdFeature, mentions.flatMap(_.userId))
      .add(QuotedTweetIdFeature, quotedTweet.map(_.tweetId))
      .add(QuotedUserIdFeature, quotedTweet.map(_.userId))
      .add(SourceTweetIdFeature, share.map(_.sourceStatusId))
      .add(SourceUserIdFeature, share.map(_.sourceUserId))
      .add(TweetUrlsFeature, candidate.features.flatMap(_.urlsList).getOrElse(Seq.empty))
      .build()
  }
}
