package com.X.usersignalservice.signals
package common

import com.X.simclusters_v2.thriftscala.InternalId
import com.X.socialgraph.thriftscala.EdgesRequest
import com.X.socialgraph.thriftscala.EdgesResult
import com.X.socialgraph.thriftscala.PageRequest
import com.X.socialgraph.thriftscala.RelationshipType
import com.X.socialgraph.thriftscala.SocialGraphService
import com.X.socialgraph.thriftscala.SrcRelationship
import com.X.twistly.common.UserId
import com.X.usersignalservice.thriftscala.Signal
import com.X.usersignalservice.thriftscala.SignalType
import com.X.util.Duration
import com.X.util.Future
import com.X.util.Time

object SGSUtils {
  val MaxNumSocialGraphSignals = 200
  val MaxAge: Duration = Duration.fromDays(90)

  def getSGSRawSignals(
    userId: UserId,
    sgsClient: SocialGraphService.MethodPerEndpoint,
    relationshipType: RelationshipType,
    signalType: SignalType,
  ): Future[Option[Seq[Signal]]] = {
    val edgeRequest = EdgesRequest(
      relationship = SrcRelationship(userId, relationshipType),
      pageRequest = Some(PageRequest(count = None))
    )
    val now = Time.now.inMilliseconds

    sgsClient
      .edges(Seq(edgeRequest))
      .map { sgsEdges =>
        sgsEdges.flatMap {
          case EdgesResult(edges, _, _) =>
            edges.collect {
              case edge if edge.createdAt >= now - MaxAge.inMilliseconds =>
                Signal(
                  signalType,
                  timestamp = edge.createdAt,
                  targetInternalId = Some(InternalId.UserId(edge.target)))
            }
        }
      }
      .map { signals =>
        signals
          .take(MaxNumSocialGraphSignals)
          .groupBy(_.targetInternalId)
          .mapValues(_.maxBy(_.timestamp))
          .values
          .toSeq
          .sortBy(-_.timestamp)
      }
      .map(Some(_))
  }
}
