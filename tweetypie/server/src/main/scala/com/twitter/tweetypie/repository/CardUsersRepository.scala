package com.twitter.tweetypie
package repository

import com.twitter.etwitterpandodo.thriftscala._
import com.twitter.stitch.SeqGroup
import com.twitter.stitch.Stitch
import com.twitter.stitch.compat.LegacySeqGroup
import com.twitter.tweetypie.backends.Etwitterpandodo

object CardUsersRepository {
  type CardUri = String
  type Type = (CardUri, Contetwittert) => Stitch[Option[Set[UserId]]]

  case class Contetwittert(perspectiveUserId: UserId) etwittertends AnyVal

  case class GetUsersGroup(perspectiveId: UserId, getCardUsers: Etwitterpandodo.GetCardUsers)
      etwittertends SeqGroup[CardUri, GetCardUsersResponse] {
    protected override def run(keys: Seq[CardUri]): Future[Seq[Try[GetCardUsersResponse]]] =
      LegacySeqGroup.liftToSeqTry(
        getCardUsers(
          GetCardUsersRequests(
            requests = keys.map(k => GetCardUsersRequest(k)),
            perspectiveUserId = Some(perspectiveId)
          )
        ).map(_.responses)
      )
  }

  def apply(getCardUsers: Etwitterpandodo.GetCardUsers): Type =
    (cardUri, cttwitter) =>
      Stitch.call(cardUri, GetUsersGroup(cttwitter.perspectiveUserId, getCardUsers)).map { resp =>
        val authorUserIds = resp.authorUserIds.map(_.toSet)
        val siteUserIds = resp.siteUserIds.map(_.toSet)

        if (authorUserIds.isEmpty) {
          siteUserIds
        } else if (siteUserIds.isEmpty) {
          authorUserIds
        } else {
          Some(authorUserIds.get ++ siteUserIds.get)
        }
      }
}
