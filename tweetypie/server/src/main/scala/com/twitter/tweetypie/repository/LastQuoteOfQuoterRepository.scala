package com.twitter.tweetypie
package repository

import com.twitter.flockdb.client.QuoteTweetsIndexGraph
import com.twitter.flockdb.client.TFlockClient
import com.twitter.flockdb.client.UserTimelineGraph
import com.twitter.stitch.Stitch

object LastQuoteOfQuoterRepository {
  type Type = (TweetId, UserId) => Stitch[Boolean]

  def apply(
    tflockReadClient: TFlockClient
  ): Type =
    (tweetId, userId) => {
      // Select the tweets authored by userId quoting tweetId.
      // By intersecting the tweet quotes with this user's tweets.
      val quotesFromQuotingUser = QuoteTweetsIndexGraph
        .from(tweetId)
        .intersect(UserTimelineGraph.from(userId))

      Stitch.callFuture(tflockReadClient.selectAll(quotesFromQuotingUser).map(_.size <= 1))
    }
}
