package com.twitter.tweetypie.repository

import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.tweetypie.UserId

object StratoSuperFollowEligibleRepository {
  type Type = UserId => Stitch[Boolean]

  val column = "audiencerewards/audienceRewardsService/getSuperFollowEligibility.User"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[UserId, Unit, Boolean] =
      client.fetcher[UserId, Boolean](column)

    userId => fetcher.fetch(userId).map(_.v.getOrElse(false))
  }
}
