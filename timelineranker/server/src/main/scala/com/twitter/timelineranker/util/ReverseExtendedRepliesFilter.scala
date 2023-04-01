package com.twitter.timelineranker.util

import com.twitter.timelines.model.TweetId
import com.twitter.timelines.model.UserId
import com.twitter.timelines.model.tweet.HydratedTweet

object ReverseExtendedRepliesFilter {
  private[util] def isQualifiedReverseExtendedReply(
    tweet: HydratedTweet,
    currentUserId: UserId,
    followedUserIds: Seq[UserId],
    mutedUserIds: Set[UserId],
    sourceTweetsById: Map[TweetId, HydratedTweet]
  ): Boolean = {
    // tweet author is out of the current user's network
    !followedUserIds.contains(tweet.userId) &&
    // tweet author is not muted
    !mutedUserIds.contains(tweet.userId) &&
    // tweet is not a retweet
    !tweet.isRetweet &&
    // there must be a source tweet
    tweet.inReplyToTweetId
      .flatMap(sourceTweetsById.get)
      .filter { sourceTweet =>
        (!sourceTweet.isRetweet) && // and it's not a retweet
        (!sourceTweet.hasReply) && // and it's not a reply
        (sourceTweet.userId != 0) && // and the author's id must be non zero
        followedUserIds.contains(sourceTweet.userId) // and the author is followed
      } // and the author has not been muted
      .exists(sourceTweet => !mutedUserIds.contains(sourceTweet.userId))
  }
}
