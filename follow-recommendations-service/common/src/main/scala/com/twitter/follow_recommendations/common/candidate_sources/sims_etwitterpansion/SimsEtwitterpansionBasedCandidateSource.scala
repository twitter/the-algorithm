package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.twitter.follow_recommendations.common.candidate_sources.base.TwoHopEtwitterpansionCandidateSource
import com.twitter.follow_recommendations.common.candidate_sources.sims.SwitchingSimsSource
import com.twitter.follow_recommendations.common.models.AccountProof
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasSimilarToContetwittert
import com.twitter.follow_recommendations.common.models.Reason
import com.twitter.follow_recommendations.common.models.SimilarToProof
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import scala.math._

case class SimilarUser(candidateId: Long, similarTo: Long, score: Double)

abstract class SimsEtwitterpansionBasedCandidateSource[-Target <: HasParams](
  switchingSimsSource: SwitchingSimsSource)
    etwittertends TwoHopEtwitterpansionCandidateSource[Target, CandidateUser, SimilarUser, CandidateUser] {

  // matwitter number secondary degree nodes per first degree node
  def matwitterSecondaryDegreeNodes(req: Target): Int

  // matwitter number output results
  def matwitterResults(req: Target): Int

  // scorer to score candidate based on first and second degree node scores
  def scoreCandidate(source: Double, similarToScore: Double): Double

  def calibrateDivisor(req: Target): Double

  def calibrateScore(candidateScore: Double, req: Target): Double = {
    candidateScore / calibrateDivisor(req)
  }

  override def secondaryDegreeNodes(req: Target, node: CandidateUser): Stitch[Seq[SimilarUser]] = {
    switchingSimsSource(new HasParams with HasSimilarToContetwittert {
      override val similarToUserIds = Seq(node.id)
      override val params = (req.params)
    }).map(_.take(matwitterSecondaryDegreeNodes(req)).map { candidate =>
      SimilarUser(
        candidate.id,
        node.id,
        (node.score, candidate.score) match {
          // only calibrated sims etwitterpanded candidates scores
          case (Some(nodeScore), Some(candidateScore)) =>
            calibrateScore(scoreCandidate(nodeScore, candidateScore), req)
          case (Some(nodeScore), _) => nodeScore
          // NewFollowingSimilarUser will enter this case
          case _ => calibrateScore(candidate.score.getOrElse(0.0), req)
        }
      )
    })
  }

  override def aggregateAndScore(
    request: Target,
    firstDegreeToSecondDegreeNodesMap: Map[CandidateUser, Seq[SimilarUser]]
  ): Stitch[Seq[CandidateUser]] = {

    val inputNodes = firstDegreeToSecondDegreeNodesMap.keys.map(_.id).toSet
    val aggregator = request.params(SimsEtwitterpansionSourceParams.Aggregator) match {
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

object SimsEtwitterpansionBasedCandidateSource {
  object ScoreAggregator {
    val Matwitter: Seq[Double] => Double = (candidateScores: Seq[Double]) => {
      if (candidateScores.size > 0) candidateScores.matwitter else 0.0
    }
    val Sum: Seq[Double] => Double = (candidateScores: Seq[Double]) => {
      candidateScores.sum
    }
    val MultiDecay: Seq[Double] => Double = (candidateScores: Seq[Double]) => {
      val alpha = 0.1
      val beta = 0.1
      val gamma = 0.8
      val decay_scores: Seq[Double] =
        candidateScores
          .sorted(Ordering[Double].reverse)
          .zipWithIndetwitter
          .map(twitter => twitter._1 * pow(gamma, twitter._2))
      alpha * candidateScores.matwitter + decay_scores.sum + beta * candidateScores.size
    }
  }
}
