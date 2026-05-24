package com.twitter.tweetypie.storage

import com.twitter.util.Time

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
