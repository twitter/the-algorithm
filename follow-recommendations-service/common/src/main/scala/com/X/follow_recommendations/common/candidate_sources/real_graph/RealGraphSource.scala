package com.X.follow_recommendations.common.candidate_sources.real_graph

import com.X.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This source gets the already followed edges from the real graph column as a candidate source.
 */
@Singleton
class RealGraphSource @Inject() (
  realGraph: RealTimeRealGraphClient)
    extends CandidateSource[HasParams with HasClientContext, CandidateUser] {
  override val identifier: CandidateSourceIdentifier = RealGraphSource.Identifier

  override def apply(request: HasParams with HasClientContext): Stitch[Seq[CandidateUser]] = {
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
