package com.X.tweetypie.repository

import com.X.stitch.Stitch
import com.X.tweetypie.TweetId
import com.X.tweetypie.core.TweetResult

object TweetResultRepository {
  type Type = (TweetId, TweetQuery.Options) => Stitch[TweetResult]

  /**
   * Short-circuits the request of invalid tweet ids (`<= 0`) by immediately throwing `NotFound`.
   */
  def shortCircuitInvalidIds(repo: Type): Type = {
    case (tweetId, _) if tweetId <= 0 => Stitch.NotFound
    case (tweetId, options) => repo(tweetId, options)
  }
}
