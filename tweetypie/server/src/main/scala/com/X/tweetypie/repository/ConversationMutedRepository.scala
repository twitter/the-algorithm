package com.X.tweetypie
package repository

import com.X.stitch.Stitch

object ConversationMutedRepository {

  /**
   * Same type as com.X.stitch.timelineservice.TimelineService.GetConversationMuted but
   * without using Arrow.
   */
  type Type = (UserId, TweetId) => Stitch[Boolean]
}
