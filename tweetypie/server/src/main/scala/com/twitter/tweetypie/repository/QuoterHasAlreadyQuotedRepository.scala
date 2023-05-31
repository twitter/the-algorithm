package com.twitter.tweetypie
package repository

import com.twitter.flockdb.client.QuotersGraph
import com.twitter.flockdb.client.TFlockClient
import com.twitter.stitch.Stitch

object QuoterHasAlreadyQuotedRepository {
  type Type = (TweetId, UserId) => Stitch[Boolean]

  def apply(
    tflockReadClient: TFlockClient
  ): Type =
    (tweetId, userId) => Stitch.callFuture(tflockReadClient.contains(QuotersGraph, tweetId, userId))
}
