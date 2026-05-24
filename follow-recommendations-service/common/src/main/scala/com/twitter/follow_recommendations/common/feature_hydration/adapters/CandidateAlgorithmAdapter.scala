package com.twitter.follow_recommendations.common.feature_hydration.adapters

import com.twitter.follow_recommendations.common.models.UserCandidateSourceDetails
import com.twitter.hermit.constants.AlgorithmFeedbackTokens.AlgorithmToFeedbackTokenMap
import com.twitter.hermit.model.Algorithm
import com.twitter.hermit.model.Algorithm.Algorithm
import com.twitter.hermit.model.Algorithm.UttProducerOfflineMbcgV1
import com.twitter.hermit.model.Algorithm.UttProducerOnlineMbcgV1
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.Feature.SparseBinary
import com.twitter.ml.api.Feature.SparseContinuous
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.IRecordOneToOneAdapter
import com.twitter.ml.api.util.FDsl._

object CandidateAlgorithmAdapter
    extends IRecordOneToOneAdapter[Option[UserCandidateSourceDetails]] {

  val CANDIDATE_ALGORITHMS: SparseBinary = new SparseBinary("candidate.source.algorithm_ids")
  val CANDIDATE_SOURCE_SCORES: SparseContinuous =
    new SparseContinuous("candidate.source.scores")
  val CANDIDATE_SOURCE_RANKS: SparseContinuous =
    new SparseContinuous("candidate.source.ranks")

  override val getFeatureContext: FeatureContext = new FeatureContext(
    CANDIDATE_ALGORITHMS,
    CANDIDATE_SOURCE_SCORES,
    CANDIDATE_SOURCE_RANKS
  )

  /** list of candidate source remaps to avoid creating different features for experimental sources.
   *  the LHS should contain the experimental source, and the RHS should contain the prod source.
   */
  def remapCandidateSource(a: Algorithm): Algorithm = a match {
    case UttProducerOnlineMbcgV1 => UttProducerOfflineMbcgV1
    case _ => a
  }

  // add the list of algorithm feedback tokens (integers) as a sparse binary feature
  override def adaptToDataRecord(
    userCandidateSourceDetailsOpt: Option[UserCandidateSourceDetails]
  ): DataRecord = {
    val dr = new DataRecord()
    userCandidateSourceDetailsOpt.foreach { userCandidateSourceDetails =>
      val scoreMap = for {
        (source, scoreOpt) <- userCandidateSourceDetails.candidateSourceScores
        score <- scoreOpt
        algo <- Algorithm.withNameOpt(source.name)
        algoId <- AlgorithmToFeedbackTokenMap.get(remapCandidateSource(algo))
      } yield algoId.toString -> score
      val rankMap = for {
        (source, rank) <- userCandidateSourceDetails.candidateSourceRanks
        algo <- Algorithm.withNameOpt(source.name)
        algoId <- AlgorithmToFeedbackTokenMap.get(remapCandidateSource(algo))
      } yield algoId.toString -> rank.toDouble

      val algoIds = scoreMap.keys.toSet ++ rankMap.keys.toSet

      // hydrate if not empty
      if (rankMap.nonEmpty) {
        dr.setFeatureValue(CANDIDATE_SOURCE_RANKS, rankMap)
      }
      if (scoreMap.nonEmpty) {
        dr.setFeatureValue(CANDIDATE_SOURCE_SCORES, scoreMap)
      }
      if (algoIds.nonEmpty) {
        dr.setFeatureValue(CANDIDATE_ALGORITHMS, algoIds)
      }
    }
    dr
  }
}
