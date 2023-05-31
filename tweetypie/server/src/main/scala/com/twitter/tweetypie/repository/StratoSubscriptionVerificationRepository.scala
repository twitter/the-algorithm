package com.twitter.tweetypie.repository

import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.tweetypie.UserId
import com.twitter.strato.client.{Client => StratoClient}

object StratoSubscriptionVerificationRepository {
  type Type = (UserId, String) => Stitch[Boolean]

  val column = "subscription-services/subscription-verification/cacheProtectedHasAccess.User"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[UserId, Seq[String], Seq[String]] =
      client.fetcher[UserId, Seq[String], Seq[String]](column)

    (userId, resource) => fetcher.fetch(userId, Seq(resource)).map(f => f.v.nonEmpty)
  }
}
