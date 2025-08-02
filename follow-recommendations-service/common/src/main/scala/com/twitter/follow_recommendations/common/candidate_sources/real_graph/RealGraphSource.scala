package com.twitter.follow_recommendations.common.candidate_sources.real_graph

import com.twitter.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * This source gets the already followed edges from the real graph column as a candidate source.
 */
@Singleton
class RealGraphSource @Inject() (
  realGraph: RealTimeRealGraphClient)
    etwittertends CandidateSource[HasParams with HasClientContetwittert, CandidateUser] {
  override val identifier: CandidateSourceIdentifier = RealGraphSource.Identifier

  override def apply(request: HasParams with HasClientContetwittert): Stitch[Seq[CandidateUser]] = {
    request.getOptionalUserId
      .map { userId =>
        realGraph.getRealGraphWeights(userId).map { scoreMap =>
          scoreMap.map {
            case (candidateId, realGraphScore) =>
              CandidateUser(id = candidateId, score = Some(realGraphScore))
                .withCandidateSource(identifier)
          }.toSeq
        }
      }.getOrElse(Stitch.Nil)
  }
}

object RealGraphSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.RealGraphFollowed.toString)
}
