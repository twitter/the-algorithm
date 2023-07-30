package com.X.tweetypie.repository

import com.X.stitch.Stitch
import com.X.tweetypie.CommunityId
import com.X.strato.client.Fetcher
import com.X.strato.client.{Client => StratoClient}

object StratoCommunityMembershipRepository {
  type Type = CommunityId => Stitch[Boolean]

  val column = "communities/isMember.Community"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[CommunityId, Unit, Boolean] =
      client.fetcher[CommunityId, Boolean](column)

    communityId => fetcher.fetch(communityId).map(_.v.getOrElse(false))
  }
}
