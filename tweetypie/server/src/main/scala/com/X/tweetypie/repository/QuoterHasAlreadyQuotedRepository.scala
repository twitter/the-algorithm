package com.X.tweetypie
package repository

import com.X.flockdb.client.QuotersGraph
import com.X.flockdb.client.TFlockClient
import com.X.stitch.Stitch

object QuoterHasAlreadyQuotedRepository {
  type Type = (TweetId, UserId) => Stitch[Boolean]

  def apply(
    tflockReadClient: TFlockClient
  ): Type =
    (tweetId, userId) => Stitch.callFuture(tflockReadClient.contains(QuotersGraph, tweetId, userId))
}
