package com.X.visibility.builder.tweets

import com.X.finagle.stats.StatsReceiver
import com.X.stitch.Stitch
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.builder.users.ViewerVerbsAuthor
import com.X.visibility.common.UserRelationshipSource
import com.X.visibility.features.TweetIsExclusiveTweet
import com.X.visibility.features.ViewerIsExclusiveTweetRootAuthor
import com.X.visibility.features.ViewerSuperFollowsExclusiveTweetRootAuthor
import com.X.visibility.models.ViewerContext

class ExclusiveTweetFeatures(
  userRelationshipSource: UserRelationshipSource,
  statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver = statsReceiver.scope("exclusive_tweet_features")
  private[this] val viewerSuperFollowsAuthor =
    scopedStatsReceiver.scope(ViewerSuperFollowsExclusiveTweetRootAuthor.name).counter("requests")

  def rootAuthorId(tweet: Tweet): Option[Long] =
    tweet.exclusiveTweetControl.map(_.conversationAuthorId)

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
    viewerContext: ViewerContext
  ): FeatureMapBuilder => FeatureMapBuilder = {
    val viewerId = viewerContext.userId

    _.withConstantFeature(TweetIsExclusiveTweet, tweet.exclusiveTweetControl.isDefined)
      .withConstantFeature(ViewerIsExclusiveTweetRootAuthor, viewerIsRootAuthor(tweet, viewerId))
      .withFeature(
        ViewerSuperFollowsExclusiveTweetRootAuthor,
        viewerSuperFollowsRootAuthor(tweet, viewerId))
  }

  def forTweetOnly(tweet: Tweet): FeatureMapBuilder => FeatureMapBuilder = {
    _.withConstantFeature(TweetIsExclusiveTweet, tweet.exclusiveTweetControl.isDefined)
  }
}
