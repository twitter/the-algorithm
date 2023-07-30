package com.X.visibility.models

import com.X.tweetypie.thriftscala.Communities
import com.X.tweetypie.thriftscala.Tweet

object CommunityTweet {
  def getCommunityId(communities: Communities): Option[CommunityId] =
    communities.communityIds.headOption

  def getCommunityId(tweet: Tweet): Option[CommunityId] =
    tweet.communities.flatMap(getCommunityId)

  def apply(tweet: Tweet): Option[CommunityTweet] =
    getCommunityId(tweet).map { communityId =>
      val authorId = tweet.coreData.get.userId
      CommunityTweet(tweet, communityId, authorId)
    }
}

case class CommunityTweet(
  tweet: Tweet,
  communityId: CommunityId,
  authorId: Long)
