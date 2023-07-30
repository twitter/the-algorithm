package com.X.tweetypie.storage

import com.X.util.Time

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
