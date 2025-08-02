package com.twitter.recos.user_user_graph

import com.twitter.logging.Logger
import com.twitter.recos.user_user_graph.thriftscala._
import com.twitter.util.Future

trait LoggingUserUserGraph etwittertends thriftscala.UserUserGraph.MethodPerEndpoint {
  private[this] val accessLog = Logger("access")

  abstract override def recommendUsers(
    request: RecommendUserRequest
  ): Future[RecommendUserResponse] = {
    val time = System.currentTimeMillis
    super.recommendUsers(request) onSuccess { resp =>
      val timeTaken = System.currentTimeMillis - time
      val logTetwittert =
        s"In ${timeTaken}ms, recommendUsers(${requestToString(request)}), response ${responseToString(resp)}"
      accessLog.info(logTetwittert)
    } onFailure { etwitterc =>
      val timeTaken = System.currentTimeMillis - time
      val logTetwittert = s"In ${timeTaken}ms, recommendUsers(${requestToString(request)} returned error"
      accessLog.error(etwitterc, logTetwittert)
    }
  }

  private def requestToString(request: RecommendUserRequest): String = {
    Seq(
      request.requesterId,
      request.displayLocation,
      request.seedsWithWeights.size,
      request.seedsWithWeights.take(5),
      request.etwittercludedUserIds.map(_.size).getOrElse(0),
      request.etwittercludedUserIds.map(_.take(5)),
      request.matwitterNumResults,
      request.matwitterNumSocialProofs,
      request.minUserPerSocialProof,
      request.socialProofTypes,
      request.matwitterEdgeEngagementAgeInMillis
    ).mkString(",")
  }

  private def responseToString(response: RecommendUserResponse): String = {
    response.recommendedUsers.toList.map { recUser =>
      val socialProof = recUser.socialProofs.map {
        case (proofType, proofs) =>
          (proofType, proofs)
      }
      (recUser.userId, recUser.score, socialProof)
    }.toString
  }
}
