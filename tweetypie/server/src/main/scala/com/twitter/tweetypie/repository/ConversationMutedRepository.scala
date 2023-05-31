package com.twitter.tweetypie
package repository

import com.twitter.stitch.Stitch

object ConversationMutedRepository {

  /**
   * Same type as com.twitter.stitch.timelineservice.TimelineService.GetConversationMuted but
   * without using Arrow.
   */
  type Type = (UserId, TweetId) => Stitch[Boolean]
}
