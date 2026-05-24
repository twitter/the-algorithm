package com.twitter.timelineranker.util

import com.twitter.timelines.model.UserId
import com.twitter.timelines.model.tweet.HydratedTweet

object RecommendedRepliesFilter {
  private[util] def isRecommendedReply(
    tweet: HydratedTweet,
    followedUserIds: Seq[UserId]
  ): Boolean = {
    tweet.hasReply && tweet.inReplyToTweetId.nonEmpty &&
    (!followedUserIds.contains(tweet.userId))
  }

  private[util] def isRecommendedReplyToNotFollowedUser(
    tweet: HydratedTweet,
    viewingUserId: UserId,
    followedUserIds: Seq[UserId],
    mutedUserIds: Set[UserId]
  ): Boolean = {
    val isValidRecommendedReply =
      !tweet.isRetweet &&
        tweet.inReplyToUserId.exists(followedUserIds.contains(_)) &&
        !mutedUserIds.contains(tweet.userId)

    isRecommendedReply(tweet, followedUserIds) && !isValidRecommendedReply
  }
}
