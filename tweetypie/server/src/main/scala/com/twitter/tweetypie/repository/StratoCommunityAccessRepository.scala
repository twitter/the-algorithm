package com.twitter.tweetypie.repository

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.CommunityId
import com.twitter.strato.client.Fetcher
import com.twitter.strato.client.{Client => StratoClient}

object StratoCommunityAccessRepository {
  type Type = CommunityId => Stitch[Option[CommunityAccess]]

  sealed trait CommunityAccess
  object CommunityAccess {
    case object Public extends CommunityAccess
    case object Closed extends CommunityAccess
    case object Private extends CommunityAccess
  }

  val column = "communities/access.Community"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[CommunityId, Unit, CommunityAccess] =
      client.fetcher[CommunityId, CommunityAccess](column)

    communityId => fetcher.fetch(communityId).map(_.v)
  }
}
