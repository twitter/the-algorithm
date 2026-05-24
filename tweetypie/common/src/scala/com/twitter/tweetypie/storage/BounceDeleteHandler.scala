package com.twitter.tweetypie.storage

import com.twitter.util.Time

object BounceDeleteHandler {
  def apply(
    insert: ManhattanOperations.Insert,
    scribe: Scribe
  ): TweetStorageClient.BounceDelete =
    tweetId => {
      val mhTimestamp = Time.now
      val bounceDeleteRecord = TweetStateRecord
        .BounceDeleted(tweetId, mhTimestamp.inMillis)
        .toTweetMhRecord

      insert(bounceDeleteRecord).onSuccess { _ =>
        scribe.logRemoved(tweetId, mhTimestamp, isSoftDeleted = true)
      }
    }
}
