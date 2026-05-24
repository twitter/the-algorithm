package com.twitter.home_mixer.functional_component.scorer

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.model.HomeFeatures.PhoenixScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedScoreFeature.PhoenixPredictedScoreFeatures
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixInferenceClusterParam
import com.twitter.home_mixer.util.PhoenixScorerStatsHandler
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
case class PhoenixModelRerankingScorer @Inject() (statsReceiver: StatsReceiver)
    extends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("PhoenixModelReranking")

  override val features: Set[Feature[_, _]] = Set(
    PhoenixScoreFeature,
    // WeightedModelScoreFeature, remove temporarily to avoid overwriting navi weighted score
    DebugStringFeature
  )

  private val StatsReadabilityMultiplier = 1000
  private val modelStatsHandler =
    new PhoenixScorerStatsHandler(statsReceiver, getClass.getSimpleName)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val cluster = query.params(PhoenixInferenceClusterParam).toString
    val modelStats = modelStatsHandler.getModelStats(query, cluster)

    val scoresAndWeightsSeq = candidates.map { candidate =>
      PhoenixPredictedScoreFeatures.map { feature =>
        val predictions = feature.extractScore(candidate.features, query)
        modelStats.trackPredictedScoreStats(feature, predictions)
        val isEligible = feature.isEligible(candidate.features)
        val score = if (predictions.nonEmpty && isEligible) predictions.max else 0.0
        val weight = query.params(feature.modelWeightParam)
        (score, weight)
      }
    }
    val transformedScoresAndWeightsSeq = getScoresWithPerHeadMax(scoresAndWeightsSeq)

    val debugStrings: Seq[String] =
      candidates.map(_.features.getOrElse(DebugStringFeature, None).getOrElse(""))

    val featureMaps = transformedScoresAndWeightsSeq
      .zip(debugStrings)
      .map {
        case (transformedScores, debugStr) =>
          val finalScore =
            aggregateWeightedScores(query, transformedScores, modelStats.negativeFilterCounter)
          val featureNames = PhoenixPredictedScoreFeatures.map(_.featureName)
          modelStats.scoreStat.add((finalScore * StatsReadabilityMultiplier).toFloat)

          val updatedDebugStr =
            computeDebugMetadata(debugStr, featureNames, transformedScores, finalScore)

          FeatureMapBuilder()
            .add(PhoenixScoreFeature, Some(finalScore))
            .add(DebugStringFeature, Some(updatedDebugStr))
            .build()
      }

    Stitch.value(featureMaps)
  }

}
