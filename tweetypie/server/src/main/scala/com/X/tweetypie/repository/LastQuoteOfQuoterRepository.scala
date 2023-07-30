package com.X.tweetypie
package repository

import com.X.flockdb.client.QuoteTweetsIndexGraph
import com.X.flockdb.client.TFlockClient
import com.X.flockdb.client.UserTimelineGraph
import com.X.stitch.Stitch

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
