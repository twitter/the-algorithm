package com.X.visibility.builder.tweets

import com.X.finagle.stats.StatsReceiver
import com.X.stitch.Stitch
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.builder.users.ViewerVerbsAuthor
import com.X.visibility.common.UserId
import com.X.visibility.common.UserRelationshipSource
import com.X.visibility.features._
import com.X.visibility.models.ViolationLevel

class FosnrRelationshipFeatures(
  tweetLabels: TweetLabels,
  userRelationshipSource: UserRelationshipSource,
  statsReceiver: StatsReceiver) {

  private[this] val scopedStatsReceiver = statsReceiver.scope("fonsr_relationship_features")

  private[this] val requests = scopedStatsReceiver.counter("requests")

  private[this] val viewerFollowsAuthorOfViolatingTweet =
    scopedStatsReceiver.scope(ViewerFollowsAuthorOfViolatingTweet.name).counter("requests")

  private[this] val viewerDoesNotFollowAuthorOfViolatingTweet =
    scopedStatsReceiver.scope(ViewerDoesNotFollowAuthorOfViolatingTweet.name).counter("requests")

  def forTweetAndAuthorId(
    tweet: Tweet,
    authorId: Long,
    viewerId: Option[Long]
  ): FeatureMapBuilder => FeatureMapBuilder = {
    requests.incr()
    _.withFeature(
      ViewerFollowsAuthorOfViolatingTweet,
      viewerFollowsAuthorOfViolatingTweet(tweet, authorId, viewerId))
      .withFeature(
        ViewerDoesNotFollowAuthorOfViolatingTweet,
        viewerDoesNotFollowAuthorOfViolatingTweet(tweet, authorId, viewerId))
  }

  def viewerFollowsAuthorOfViolatingTweet(
    tweet: Tweet,
    authorId: UserId,
    viewerId: Option[UserId]
  ): Stitch[Boolean] =
    tweetLabels.forTweet(tweet).flatMap { safetyLabels =>
      if (safetyLabels
          .map(ViolationLevel.fromTweetSafetyLabelOpt).collect {
            case Some(level) => level
          }.isEmpty) {
        Stitch.False
      } else {
        ViewerVerbsAuthor(
          authorId,
          viewerId,
          userRelationshipSource.follows,
          viewerFollowsAuthorOfViolatingTweet)
      }
    }

  def viewerDoesNotFollowAuthorOfViolatingTweet(
    tweet: Tweet,
    authorId: UserId,
    viewerId: Option[UserId]
  ): Stitch[Boolean] =
    tweetLabels.forTweet(tweet).flatMap { safetyLabels =>
      if (safetyLabels
          .map(ViolationLevel.fromTweetSafetyLabelOpt).collect {
            case Some(level) => level
          }.isEmpty) {
        Stitch.False
      } else {
        ViewerVerbsAuthor(
          authorId,
          viewerId,
          userRelationshipSource.follows,
          viewerDoesNotFollowAuthorOfViolatingTweet).map(following => !following)
      }
    }

}
