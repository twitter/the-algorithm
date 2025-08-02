package com.twitter.home_mitwitterer.product.scored_tweets.response_transformer

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.CandidateSourceIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.DirectedAtUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.EarlybirdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.EarlybirdScoreFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.EtwitterclusiveConversationAuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.HasImageFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRandomTweetFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.MentionScreenNameFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.MentionUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.QuotedUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.StreamToKafkaFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.home_mitwitterer.util.tweetypie.content.TweetMediaFeaturesEtwittertractor
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.timelineranker.{thriftscala => tlr}

object TimelineRankerResponseTransformer {

  val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    CandidateSourceIdFeature,
    DirectedAtUserIdFeature,
    EarlybirdFeature,
    EarlybirdScoreFeature,
    EtwitterclusiveConversationAuthorIdFeature,
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
    val quotedTweet = tweet.filter(_.quotedTweet.etwitterists(_.tweetId != 0)).flatMap(_.quotedTweet)
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
        EtwitterclusiveConversationAuthorIdFeature,
        tweet.flatMap(_.etwitterclusiveTweetControl.map(_.conversationAuthorId)))
      .add(FromInNetworkSourceFeature, false)
      .add(HasImageFeature, tweet.etwitterists(TweetMediaFeaturesEtwittertractor.hasImage))
      .add(HasVideoFeature, tweet.etwitterists(TweetMediaFeaturesEtwittertractor.hasVideo))
      .add(InReplyToTweetIdFeature, reply.flatMap(_.inReplyToStatusId))
      .add(InReplyToUserIdFeature, reply.map(_.inReplyToUserId))
      .add(IsRandomTweetFeature, candidate.features.etwitterists(_.isRandomTweet.getOrElse(false)))
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
