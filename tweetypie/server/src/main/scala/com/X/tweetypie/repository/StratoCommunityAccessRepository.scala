package com.X.tweetypie.repository

import com.X.stitch.Stitch
import com.X.tweetypie.CommunityId
import com.X.strato.client.Fetcher
import com.X.strato.client.{Client => StratoClient}

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
