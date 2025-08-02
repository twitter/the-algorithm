package com.twitter.tweetypie
package repository

import com.twitter.servo.util.FutureArrow
import com.twitter.socialgraph.thriftscala._
import com.twitter.stitch.SeqGroup
import com.twitter.stitch.Stitch
import com.twitter.stitch.compat.LegacySeqGroup

object RelationshipKey {
  def blocks(sourceId: UserId, destinationId: UserId): RelationshipKey =
    RelationshipKey(sourceId, destinationId, RelationshipType.Blocking)

  def follows(sourceId: UserId, destinationId: UserId): RelationshipKey =
    RelationshipKey(sourceId, destinationId, RelationshipType.Following)

  def mutes(sourceId: UserId, destinationId: UserId): RelationshipKey =
    RelationshipKey(sourceId, destinationId, RelationshipType.Muting)

  def reported(sourceId: UserId, destinationId: UserId): RelationshipKey =
    RelationshipKey(sourceId, destinationId, RelationshipType.ReportedAsSpam)
}

case class RelationshipKey(
  sourceId: UserId,
  destinationId: UserId,
  relationship: RelationshipType) {
  def asEtwitteristsRequest: EtwitteristsRequest =
    EtwitteristsRequest(
      source = sourceId,
      target = destinationId,
      relationships = Seq(Relationship(relationship))
    )
}

object RelationshipRepository {
  type Type = RelationshipKey => Stitch[Boolean]

  def apply(
    etwitterists: FutureArrow[(Seq[EtwitteristsRequest], Option[RequestContetwittert]), Seq[EtwitteristsResult]],
    matwitterRequestSize: Int
  ): Type = {
    val relationshipGroup: SeqGroup[RelationshipKey, Boolean] =
      new SeqGroup[RelationshipKey, Boolean] {
        override def run(keys: Seq[RelationshipKey]): Future[Seq[Try[Boolean]]] =
          LegacySeqGroup.liftToSeqTry(
            etwitterists((keys.map(_.asEtwitteristsRequest), None)).map(_.map(_.etwitterists)))
        override val matwitterSize: Int = matwitterRequestSize
      }

    relationshipKey => Stitch.call(relationshipKey, relationshipGroup)
  }
}
