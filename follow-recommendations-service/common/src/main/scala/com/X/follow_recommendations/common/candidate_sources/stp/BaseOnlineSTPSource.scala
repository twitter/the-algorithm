package com.X.follow_recommendations.common.candidate_sources.stp

import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.X.follow_recommendations.common.models.STPGraph
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams
import com.X.util.logging.Logging
import com.X.wtf.scalding.jobs.strong_tie_prediction.STPFeatureGenerator
import com.X.wtf.scalding.jobs.strong_tie_prediction.STPRecord

abstract class BaseOnlineSTPSource(
  stpGraphBuilder: STPGraphBuilder,
  baseStatsReceiver: StatsReceiver)
    extends CandidateSource[
      HasClientContext with HasParams with HasRecentFollowedUserIds,
      CandidateUser
    ]
    with Logging {

  protected val statsReceiver: StatsReceiver = baseStatsReceiver.scope("online_stp")

  override val identifier: CandidateSourceIdentifier = BaseOnlineSTPSource.Identifier

  def getCandidates(
    records: Seq[STPRecord],
    request: HasClientContext with HasParams with HasRecentFollowedUserIds
  ): Stitch[Seq[CandidateUser]]

  override def apply(
    request: HasClientContext with HasParams with HasRecentFollowedUserIds
  ): Stitch[Seq[CandidateUser]] =
    request.getOptionalUserId
      .map { userId =>
        stpGraphBuilder(request)
          .flatMap { graph: STPGraph =>
            logger.debug(graph)
            val records = STPFeatureGenerator.constructFeatures(
              userId,
              graph.firstDegreeEdgeInfoList,
              graph.secondDegreeEdgeInfoList)
            getCandidates(records, request)
          }
      }.getOrElse(Stitch.Nil)
}

object BaseOnlineSTPSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.OnlineStrongTiePredictionRecNoCaching.toString)
}
