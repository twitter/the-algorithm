package com.twitter.home_mixer.util

import com.twitter.finagle.stats.Counter
import com.twitter.home_mixer.model.PredictedScoreFeature.PredictedScoreFeatures
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ConstantNegativeHead
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.EnableNegSectionRankingParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.NegativeScoreConstantFilterThresholdParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.NegativeScoreNormFilterThresholdParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.NormalizedNegativeHead
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.UseWeightForNegHeadParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object RerankerUtil {
  val Epsilon = 0.001

  def computeModelScores(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate],
    modelStatsOpt: Option[ModelStats] = None
  ): Seq[(Double, Double)] = {
    PredictedScoreFeatures.map { predictedScoreFeature =>
      val predictedScoreOpt = predictedScoreFeature.extractScore(candidate.features, query)

      modelStatsOpt.foreach(_.trackPredictedScoreStats(predictedScoreFeature, predictedScoreOpt))

      val weight =
        query.features.flatMap(_.get(predictedScoreFeature.weightQueryFeature)).getOrElse(0.0)

      val bias = predictedScoreFeature.biasQueryFeature
        .flatMap(feature => query.features.flatMap(_.get(feature))).getOrElse(0.0)

      val score =
        if (predictedScoreFeature.isEligible(candidate.features, query))
          predictedScoreOpt.getOrElse(0.0)
        else bias

      (score, weight)
    }
  }

  def getScoresWithPerHeadMax(
    scoresAndWeightsSeq: Seq[Seq[(Double, Double)]]
  ): Seq[Seq[(Double, Double, Double)]] = {
    if (scoresAndWeightsSeq.isEmpty) Seq.empty
    else {
      // Step 1: Transpose scores to group by heads
      val headsScores: Seq[Seq[Double]] = scoresAndWeightsSeq.transpose.map { headScores =>
        headScores.map { case (score, _) => score }
      }

      // Step 2: Get max scores per head
      val headMaxScores: Seq[Double] = headsScores.map(_.max).toIndexedSeq

      // Step 3: Use max scores to get per head transformed scores
      scoresAndWeightsSeq.map { candidateScores =>
        candidateScores.zipWithIndex.map {
          case ((score, weight), headIdx) =>
            val headMaxScore = headMaxScores(headIdx)
            (score, headMaxScore, weight)
        }
      }
    }
  }

  def computeDebugMetadata(
    debugStr: String,
    featureNames: Seq[String],
    transformedScores: Seq[(Double, Double, Double)],
    finalScore: Double
  ): String = {
    assert(
      featureNames.size == transformedScores.size,
      "Feature names size doesn't matter scores size")
    val contributions: Seq[(String, Double)] = featureNames
      .zip(transformedScores)
      .collect {
        case (feature, (score, _, weight)) if weight >= 0 =>
          (feature, score * weight)
      }

    val topContributors: Seq[String] = contributions.collect {
      case (name, contrib) if finalScore > 0 && (contrib / finalScore) > 0.3 =>
        f"$name:%%.2f".format(contrib / finalScore)
    }

    debugStr + s" [${topContributors.mkString(", ")}]"
  }

  def aggregateWeightedScores(
    query: PipelineQuery,
    scoresAndWeights: Seq[(Double, Double, Double)],
    negativeFilterCounter: Counter
  ): Double = {
    val thresholdNegative = query.params(NegativeScoreConstantFilterThresholdParam)
    val thresholdNegativeNormalized = query.params(NegativeScoreNormFilterThresholdParam)
    val enableNegNormalized = query.params(NormalizedNegativeHead)
    val enableNegConstant = query.params(ConstantNegativeHead)
    val useWeightForNeg = query.params(UseWeightForNegHeadParam)
    val negSectionRanking = query.params(EnableNegSectionRankingParam)
    val (_, maxHeadScores, modelWeights) = scoresAndWeights.unzip3
    val combinedScoreSum: Double = {
      scoresAndWeights.foldLeft(0.0) {
        case (combinedScore, (score, maxHeadScore, weight)) =>
          if (weight >= 0.0 || useWeightForNeg) {
            combinedScore + score * weight
          } else {
            // Apply filtering logic only for negative weights
            val normScore = if (maxHeadScore == 0.0) 0.0 else score / maxHeadScore
            val negFilterNorm = enableNegNormalized && normScore > thresholdNegativeNormalized
            val negFilterConstant = enableNegConstant && score > thresholdNegative
            if (negFilterNorm || negFilterConstant) {
              negativeFilterCounter.incr()
              // This should be shipped and cleaned as soon as possible
              if (negSectionRanking) {
                // Assumes negative scores are not greater than 0.9 and clipped to 1
                combinedScore + weight * (1.0 min (score + 0.1))
              } else
                combinedScore + weight
            } else combinedScore
          }
      }
    }

    val positiveModelWeightsSum = modelWeights.filter(_ > 0.0).sum
    val negativeModelWeightsSum = modelWeights.filter(_ < 0).sum.abs
    val modelWeightsSum = positiveModelWeightsSum + negativeModelWeightsSum

    val weightedScoresSum =
      if (modelWeightsSum == 0) combinedScoreSum.max(0.0)
      else if (combinedScoreSum < 0)
        (combinedScoreSum + negativeModelWeightsSum) / modelWeightsSum * Epsilon
      else combinedScoreSum + Epsilon

    weightedScoresSum
  }
}
