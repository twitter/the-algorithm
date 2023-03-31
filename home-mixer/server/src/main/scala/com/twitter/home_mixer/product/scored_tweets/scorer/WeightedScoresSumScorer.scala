package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeightedScoresSumScorer @Inject() (statsReceiver: StatsReceiver)
    extends Scorer[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("WeightedScoresSum")

  override val features: Set[Feature[_, _]] = Set(WeightedModelScoreFeature, ScoreFeature)

  private val StatsReadabilityMultiplier = 1000
  private val Epsilon = 0.001
  private val PredictedScoreStatName = f"predicted_score_${StatsReadabilityMultiplier}x"
  private val MissingScoreStatName = "missing_score"

  private val scopedStatsProvider = statsReceiver.scope(getClass.getSimpleName)
  private val scoreStat = scopedStatsProvider.stat(f"score_${StatsReadabilityMultiplier}x")

  override def apply(
    query: ScoredTweetsQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val features = candidates.map { candidate =>
      val score = weightedModelScore(query, candidate.features)
      scoreStat.add((score * StatsReadabilityMultiplier).toFloat)
      FeatureMapBuilder()
        .add(WeightedModelScoreFeature, Some(score))
        .add(ScoreFeature, Some(score))
        .build()
    }

    Stitch.value(features)
  }

  /**
   * (1) compute weighted sum of predicted scores of all engagements
   * (2) convert negative score to positive score if needed
   */
  private def weightedModelScore(
    query: PipelineQuery,
    features: FeatureMap
  ): Double = {
    val weightedScoreAndModelWeightSeq: Seq[(Double, Double)] =
      HomeNaviModelDataRecordScorer.PredictedScoreFeatures.map { scoreFeature =>
        val predictedScoreOpt = features.getOrElse(scoreFeature, None)

        predictedScoreOpt match {
          case Some(predictedScore) =>
            scopedStatsProvider
              .stat(scoreFeature.statName, PredictedScoreStatName)
              .add((predictedScore * StatsReadabilityMultiplier).toFloat)
          case None =>
            scopedStatsProvider.counter(scoreFeature.statName, MissingScoreStatName).incr()
        }

        val weight = query.params(scoreFeature.modelWeightParam)
        (predictedScoreOpt.getOrElse(0.0) * weight, weight)
      }

    val (weightedScores, modelWeights) = weightedScoreAndModelWeightSeq.unzip
    val combinedScoreSum = weightedScores.sum

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
