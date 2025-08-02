package com.twitter.tweetypie.repository

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.CommunityId
import com.twitter.strato.client.Fetcher
import com.twitter.strato.client.{Client => StratoClient}

object StratoCommunityAccessRepository {
  type Type = CommunityId => Stitch[Option[CommunityAccess]]

  sealed trait CommunityAccess
  object CommunityAccess {
    case object Public etwittertends CommunityAccess
    case object Closed etwittertends CommunityAccess
    case object Private etwittertends CommunityAccess
  }

  val column = "communities/access.Community"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[CommunityId, Unit, CommunityAccess] =
      client.fetcher[CommunityId, CommunityAccess](column)

    communityId => fetcher.fetch(communityId).map(_.v)
  }
}
