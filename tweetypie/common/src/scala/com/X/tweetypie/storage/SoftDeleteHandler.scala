package com.X.tweetypie.storage

import com.X.util.Time

object SoftDeleteHandler {
  def apply(
    insert: ManhattanOperations.Insert,
    scribe: Scribe
  ): TweetStorageClient.SoftDelete =
    tweetId => {
      val mhTimestamp = Time.now
      val softDeleteRecord = TweetStateRecord
        .SoftDeleted(tweetId, mhTimestamp.inMillis)
        .toTweetMhRecord

      insert(softDeleteRecord).onSuccess { _ =>
        scribe.logRemoved(tweetId, mhTimestamp, isSoftDeleted = true)
      }
    }
}
