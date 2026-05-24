package com.twitter.home_mixer.functional_component.scorer

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.model.PredictedScoreFeature.PredictedScoreFeatures
import com.twitter.home_mixer.util.NaviScorerStatsHandler
import com.twitter.home_mixer.util.RerankerUtil._
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
case class WeighedModelRerankingScorer @Inject() (
  statsReceiver: StatsReceiver)
    extends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("WeightedModelReranking")

  override val features: Set[Feature[_, _]] = Set(
    ScoreFeature,
    WeightedModelScoreFeature,
    DebugStringFeature
  )

  private val modelStatsHandler = new NaviScorerStatsHandler(statsReceiver, getClass.getSimpleName)
  private val StatsReadabilityMultiplier = 1000

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val modelStats = modelStatsHandler.getModelStats(query)
    val scoresAndWeightsSeq = candidates.map(computeModelScores(query, _, Some(modelStats)))
    val transformedScoresAndWeightsSeq = getScoresWithPerHeadMax(scoresAndWeightsSeq)

    val debugStrings: Seq[String] =
      candidates.map(_.features.getOrElse(DebugStringFeature, None).getOrElse(""))

    val featureMaps = transformedScoresAndWeightsSeq
      .zip(debugStrings)
      .map {
        case (transformedScores, debugStr) =>
          val finalScore =
            aggregateWeightedScores(query, transformedScores, modelStats.negativeFilterCounter)
          val featureNames = PredictedScoreFeatures.map(_.statName)
          modelStats.scoreStat.add((finalScore * StatsReadabilityMultiplier).toFloat)

          val updatedDebugStr =
            computeDebugMetadata(debugStr, featureNames, transformedScores, finalScore)

          FeatureMapBuilder()
            .add(ScoreFeature, Some(finalScore))
            .add(WeightedModelScoreFeature, Some(finalScore))
            .add(DebugStringFeature, Some(updatedDebugStr))
            .build()
      }

    Stitch.value(featureMaps)
  }

}
