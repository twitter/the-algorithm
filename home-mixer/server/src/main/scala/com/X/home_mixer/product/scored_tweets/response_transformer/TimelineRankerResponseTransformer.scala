package com.X.home_mixer.product.scored_tweets.response_transformer

import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.X.home_mixer.model.HomeFeatures.DirectedAtUserIdFeature
import com.X.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.X.home_mixer.model.HomeFeatures.EarlybirdScoreFeature
import com.X.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.X.home_mixer.model.HomeFeatures.HasImageFeature
import com.X.home_mixer.model.HomeFeatures.HasVideoFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.X.home_mixer.model.HomeFeatures.IsRandomTweetFeature
import com.X.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.X.home_mixer.model.HomeFeatures.MentionScreenNameFeature
import com.X.home_mixer.model.HomeFeatures.MentionUserIdFeature
import com.X.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.QuotedUserIdFeature
import com.X.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.X.home_mixer.model.HomeFeatures.StreamToKafkaFeature
import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.X.home_mixer.util.tweetypie.content.TweetMediaFeaturesExtractor
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.timelineranker.{thriftscala => tlr}

object TimelineRankerResponseTransformer {

  val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    CandidateSourceIdFeature,
    DirectedAtUserIdFeature,
    EarlybirdFeature,
    EarlybirdScoreFeature,
    ExclusiveConversationAuthorIdFeature,
    FromInNetworkSourceFeature,
    HasImageFeature,
    HasVideoFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsRandomTweetFeature,
    IsRetweetFeature,
    MentionScreenNameFeature,
    MentionUserIdFeature,
    StreamToKafkaFeature,
    QuotedTweetIdFeature,
    QuotedUserIdFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    SuggestTypeFeature,
    TweetUrlsFeature
  )

  def transform(candidate: tlr.CandidateTweet): FeatureMap = {
    val tweet = candidate.tweet
    val quotedTweet = tweet.filter(_.quotedTweet.exists(_.tweetId != 0)).flatMap(_.quotedTweet)
    val mentions = tweet.flatMap(_.mentions).getOrElse(Seq.empty)
    val coreData = tweet.flatMap(_.coreData)
    val share = coreData.flatMap(_.share)
    val reply = coreData.flatMap(_.reply)

    FeatureMapBuilder()
      .add(AuthorIdFeature, coreData.map(_.userId))
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
      .add(IsRandomTweetFeature, candidate.features.exists(_.isRandomTweet.getOrElse(false)))
      .add(IsRetweetFeature, share.isDefined)
      .add(MentionScreenNameFeature, mentions.map(_.screenName))
      .add(MentionUserIdFeature, mentions.flatMap(_.userId))
      .add(StreamToKafkaFeature, true)
      .add(QuotedTweetIdFeature, quotedTweet.map(_.tweetId))
      .add(QuotedUserIdFeature, quotedTweet.map(_.userId))
      .add(SourceTweetIdFeature, share.map(_.sourceStatusId))
      .add(SourceUserIdFeature, share.map(_.sourceUserId))
      .add(TweetUrlsFeature, candidate.features.flatMap(_.urlsList).getOrElse(Seq.empty))
      .build()
  }
}
