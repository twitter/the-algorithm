package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.PhoenixScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePhoenixScorerParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnablePhoenixRescoreParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnablePhoenixScoreParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object PhoenixRescoringFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("PhoenixRescoring")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    // Use rescoring to change scoreFeature only when scoring didn't happen using Phoenix
    val usePhoenixRescoring =
      query.params(EnablePhoenixScorerParam) &&
        query.params(EnablePhoenixRescoreParam) &&
        !query.params(EnablePhoenixScoreParam)
    val finalScores = candidates.map { candidate =>
      val score = candidate.features.getOrElse(ScoreFeature, None).getOrElse(0.0)
      val weightedModelScore =
        candidate.features.getOrElse(WeightedModelScoreFeature, None).getOrElse(0.0)
      val phoenixScore = candidate.features.getOrElse(PhoenixScoreFeature, None).getOrElse(0.0)
      // The multiplier is for heuristics, it might not always be accurate for listwise heuristics
      if (score == 0.0 | weightedModelScore == 0.0) 0.0
      else if (usePhoenixRescoring) phoenixScore * (score / weightedModelScore)
      else score
    }
    Stitch.value(finalScores.map(score => FeatureMap(ScoreFeature, Some(score))))
  }

}
