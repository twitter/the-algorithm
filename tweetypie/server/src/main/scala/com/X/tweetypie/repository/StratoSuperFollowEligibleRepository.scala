package com.X.tweetypie.repository

import com.X.stitch.Stitch
import com.X.strato.client.Fetcher
import com.X.strato.client.{Client => StratoClient}
import com.X.tweetypie.UserId

object StratoSuperFollowEligibleRepository {
  type Type = UserId => Stitch[Boolean]

  val column = "audiencerewards/audienceRewardsService/getSuperFollowEligibility.User"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[UserId, Unit, Boolean] =
      client.fetcher[UserId, Boolean](column)

    userId => fetcher.fetch(userId).map(_.v.getOrElse(false))
  }
}
