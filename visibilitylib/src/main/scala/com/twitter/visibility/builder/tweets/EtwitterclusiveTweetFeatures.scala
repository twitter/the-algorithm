package com.twitter.visibility.builder.tweets

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.builder.FeatureMapBuilder
import com.twitter.visibility.builder.users.ViewerVerbsAuthor
import com.twitter.visibility.common.UserRelationshipSource
import com.twitter.visibility.features.TweetIsEtwitterclusiveTweet
import com.twitter.visibility.features.ViewerIsEtwitterclusiveTweetRootAuthor
import com.twitter.visibility.features.ViewerSuperFollowsEtwitterclusiveTweetRootAuthor
import com.twitter.visibility.models.ViewerContetwittert

class EtwitterclusiveTweetFeatures(
  userRelationshipSource: UserRelationshipSource,
  statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver = statsReceiver.scope("etwitterclusive_tweet_features")
  private[this] val viewerSuperFollowsAuthor =
    scopedStatsReceiver.scope(ViewerSuperFollowsEtwitterclusiveTweetRootAuthor.name).counter("requests")

  def rootAuthorId(tweet: Tweet): Option[Long] =
    tweet.etwitterclusiveTweetControl.map(_.conversationAuthorId)

  def viewerIsRootAuthor(
    tweet: Tweet,
    viewerIdOpt: Option[Long]
  ): Boolean =
    (rootAuthorId(tweet), viewerIdOpt) match {
      case (Some(rootAuthorId), Some(viewerId)) if rootAuthorId == viewerId => true
      case _ => false
    }

  def viewerSuperFollowsRootAuthor(
    tweet: Tweet,
    viewerId: Option[Long]
  ): Stitch[Boolean] =
    rootAuthorId(tweet) match {
      case Some(authorId) =>
        ViewerVerbsAuthor(
          authorId,
          viewerId,
          userRelationshipSource.superFollows,
          viewerSuperFollowsAuthor)
      case None =>
        Stitch.False
    }

  def forTweet(
    tweet: Tweet,
    viewerContetwittert: ViewerContetwittert
  ): FeatureMapBuilder => FeatureMapBuilder = {
    val viewerId = viewerContetwittert.userId

    _.withConstantFeature(TweetIsEtwitterclusiveTweet, tweet.etwitterclusiveTweetControl.isDefined)
      .withConstantFeature(ViewerIsEtwitterclusiveTweetRootAuthor, viewerIsRootAuthor(tweet, viewerId))
      .withFeature(
        ViewerSuperFollowsEtwitterclusiveTweetRootAuthor,
        viewerSuperFollowsRootAuthor(tweet, viewerId))
  }

  def forTweetOnly(tweet: Tweet): FeatureMapBuilder => FeatureMapBuilder = {
    _.withConstantFeature(TweetIsEtwitterclusiveTweet, tweet.etwitterclusiveTweetControl.isDefined)
  }
}
