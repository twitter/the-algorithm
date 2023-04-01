package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.twitter.follow_recommendations.common.models.STPGraph
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import com.twitter.util.logging.Logging
import com.twitter.wtf.scalding.jobs.strong_tie_prediction.STPFeatureGenerator
import com.twitter.wtf.scalding.jobs.strong_tie_prediction.STPRecord

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
