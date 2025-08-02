package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.candidate_sources.sims.SwitchingSimsSource
import com.twitter.follow_recommendations.common.clients.real_time_real_graph.RealTimeRealGraphClient
import com.twitter.follow_recommendations.common.models.AccountProof
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.Reason
import com.twitter.follow_recommendations.common.models.SimilarToProof
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RecentEngagementSimilarUsersSource @Inject() (
  realTimeRealGraphClient: RealTimeRealGraphClient,
  switchingSimsSource: SwitchingSimsSource,
  statsReceiver: StatsReceiver)
    etwittertends SimsEtwitterpansionBasedCandidateSource[HasClientContetwittert with HasParams](
      switchingSimsSource) {
  override def matwitterSecondaryDegreeNodes(req: HasClientContetwittert with HasParams): Int = Int.MatwitterValue

  override def matwitterResults(req: HasClientContetwittert with HasParams): Int =
    RecentEngagementSimilarUsersSource.MatwitterResults

  override val identifier: CandidateSourceIdentifier = RecentEngagementSimilarUsersSource.Identifier
  private val stats = statsReceiver.scope(identifier.name)
  private val calibratedScoreCounter = stats.counter("calibrated_scores_counter")

  override def scoreCandidate(sourceScore: Double, similarToScore: Double): Double = {
    sourceScore * similarToScore
  }

  override def calibrateDivisor(req: HasClientContetwittert with HasParams): Double = {
    req.params(DBV2SimsEtwitterpansionParams.RecentEngagementSimilarUsersDBV2CalibrateDivisor)
  }

  override def calibrateScore(
    candidateScore: Double,
    req: HasClientContetwittert with HasParams
  ): Double = {
    calibratedScoreCounter.incr()
    candidateScore / calibrateDivisor(req)
  }

  /**
   * fetch first degree nodes given request
   */
  override def firstDegreeNodes(
    target: HasClientContetwittert with HasParams
  ): Stitch[Seq[CandidateUser]] = {
    target.getOptionalUserId
      .map { userId =>
        realTimeRealGraphClient
          .getUsersRecentlyEngagedWith(
            userId,
            RealTimeRealGraphClient.EngagementScoreMap,
            includeDirectFollowCandidates = true,
            includeNonDirectFollowCandidates = true
          ).map(_.sortBy(-_.score.getOrElse(0.0d))
            .take(RecentEngagementSimilarUsersSource.MatwitterFirstDegreeNodes))
      }.getOrElse(Stitch.Nil)
  }

  override def aggregateAndScore(
    request: HasClientContetwittert with HasParams,
    firstDegreeToSecondDegreeNodesMap: Map[CandidateUser, Seq[SimilarUser]]
  ): Stitch[Seq[CandidateUser]] = {

    val inputNodes = firstDegreeToSecondDegreeNodesMap.keys.map(_.id).toSet
    val aggregator = request.params(RecentEngagementSimilarUsersParams.Aggregator) match {
      case SimsEtwitterpansionSourceAggregatorId.Matwitter =>
        SimsEtwitterpansionBasedCandidateSource.ScoreAggregator.Matwitter
      case SimsEtwitterpansionSourceAggregatorId.Sum =>
        SimsEtwitterpansionBasedCandidateSource.ScoreAggregator.Sum
      case SimsEtwitterpansionSourceAggregatorId.MultiDecay =>
        SimsEtwitterpansionBasedCandidateSource.ScoreAggregator.MultiDecay
    }

    val groupedCandidates = firstDegreeToSecondDegreeNodesMap.values.flatten
      .filterNot(c => inputNodes.contains(c.candidateId))
      .groupBy(_.candidateId)
      .map {
        case (id, candidates) =>
          // Different aggregators for final score
          val finalScore = aggregator(candidates.map(_.score).toSeq)
          val proofs = candidates.map(_.similarTo).toSet

          CandidateUser(
            id = id,
            score = Some(finalScore),
            reason =
              Some(Reason(Some(AccountProof(similarToProof = Some(SimilarToProof(proofs.toSeq))))))
          ).withCandidateSource(identifier)
      }
      .toSeq
      .sortBy(-_.score.getOrElse(0.0d))
      .take(matwitterResults(request))

    Stitch.value(groupedCandidates)
  }
}

object RecentEngagementSimilarUsersSource {
  val Identifier = CandidateSourceIdentifier(Algorithm.RecentEngagementSimilarUser.toString)
  val MatwitterFirstDegreeNodes = 10
  val MatwitterResults = 200
}
