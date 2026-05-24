package com.twitter.tweetypie.repository

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.TweetId
import com.twitter.tweetypie.core.TweetResult

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
