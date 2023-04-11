package com.twitter.visibility.builder.tweets

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.builder.FeatureMapBuilder
import com.twitter.visibility.builder.users.ViewerVerbsAuthor
import com.twitter.visibility.common.UserId
import com.twitter.visibility.common.UserRelationshipSource
import com.twitter.visibility.features._
import com.twitter.visibility.models.ViolationLevel

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
