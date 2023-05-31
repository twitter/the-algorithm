package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.thriftscala.Share

/**
 * When creating a retweet, we set parent_status_id to the tweet id that the user sent (the tweet they're retweeting).
 * Old tweets have parent_status_id set to zero.
 * When loading the old tweets, we should set parent_status_id to source_status_id if it's zero.
 */
object RetweetParentStatusIdRepairer {
  private val shareMutation =
    Mutation.fromPartial[Option[Share]] {
      case Some(share) if share.parentStatusId == 0L =>
        Some(share.copy(parentStatusId = share.sourceStatusId))
    }

  private[tweetypie] val tweetMutation = TweetLenses.share.mutation(shareMutation)
}
