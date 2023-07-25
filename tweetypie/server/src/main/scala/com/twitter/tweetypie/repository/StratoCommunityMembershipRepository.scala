package com.twitter.tweetypie.repository

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.CommunityId
import com.twitter.strato.client.Fetcher
import com.twitter.strato.client.{Client => StratoClient}

object StratoCommunityMembershipRepository {
  type Type = CommunityId => Stitch[Boolean]

  val column = "communities/isMember.Community"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[CommunityId, Unit, Boolean] =
      client.fetcher[CommunityId, Boolean](column)

    communityId => fetcher.fetch(communityId).map(_.v.getOrElse(false))
  }
}
