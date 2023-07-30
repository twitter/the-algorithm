package com.X.visibility.builder.tweets

import com.X.stitch.Stitch
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.common.TrustedFriendsListId
import com.X.visibility.common.TrustedFriendsSource
import com.X.visibility.features.TweetIsTrustedFriendTweet
import com.X.visibility.features.ViewerIsTrustedFriendOfTweetAuthor
import com.X.visibility.features.ViewerIsTrustedFriendTweetAuthor

class TrustedFriendsFeatures(trustedFriendsSource: TrustedFriendsSource) {

  private[builder] def viewerIsTrustedFriend(
    tweet: Tweet,
    viewerId: Option[Long]
  ): Stitch[Boolean] =
    (trustedFriendsListId(tweet), viewerId) match {
      case (Some(tfListId), Some(userId)) =>
        trustedFriendsSource.isTrustedFriend(tfListId, userId)
      case _ => Stitch.False
    }

  private[builder] def viewerIsTrustedFriendListOwner(
    tweet: Tweet,
    viewerId: Option[Long]
  ): Stitch[Boolean] =
    (trustedFriendsListId(tweet), viewerId) match {
      case (Some(tfListId), Some(userId)) =>
        trustedFriendsSource.isTrustedFriendListOwner(tfListId, userId)
      case _ => Stitch.False
    }

  private[builder] def trustedFriendsListId(tweet: Tweet): Option[TrustedFriendsListId] =
    tweet.trustedFriendsControl.map(_.trustedFriendsListId)

  def forTweet(
    tweet: Tweet,
    viewerId: Option[Long]
  ): FeatureMapBuilder => FeatureMapBuilder = {
    _.withConstantFeature(
      TweetIsTrustedFriendTweet,
      tweet.trustedFriendsControl.isDefined
    ).withFeature(
        ViewerIsTrustedFriendTweetAuthor,
        viewerIsTrustedFriendListOwner(tweet, viewerId)
      ).withFeature(
        ViewerIsTrustedFriendOfTweetAuthor,
        viewerIsTrustedFriend(tweet, viewerId)
      )
  }

  def forTweetOnly(tweet: Tweet): FeatureMapBuilder => FeatureMapBuilder = {
    _.withConstantFeature(TweetIsTrustedFriendTweet, tweet.trustedFriendsControl.isDefined)
  }

}
