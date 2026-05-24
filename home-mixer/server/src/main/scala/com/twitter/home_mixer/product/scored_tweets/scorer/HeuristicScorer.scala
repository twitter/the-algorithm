package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.PhoenixScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePhoenixScorerParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.EnableNoNegHeuristicParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnablePhoenixScoreParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.MtlNormalization
import com.twitter.home_mixer.util.RerankerUtil.Epsilon
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.util.MtlNormalizer

object HeuristicScorer extends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("Heuristic")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val noNegHeuristic = query.params(EnableNoNegHeuristicParam)
    val rescorers = Seq(
      RescoreOutOfNetwork,
      RescoreReplies,
      RescoreMTLNormalization(
        MtlNormalizer(
          alpha = query.params(MtlNormalization.AlphaParam) / 100.0,
          beta = query.params(MtlNormalization.BetaParam),
          gamma = query.params(MtlNormalization.GammaParam)
        )
      ),
      RescoreListwise(ContentExplorationListwiseRescoringProvider(query, candidates)),
      RescoreListwise(DeepRetrievalListwiseRescoringProvider(query, candidates)),
      RescoreListwise(EvergreenDeepRetrievalListwiseRescoringProvider(query, candidates)),
      RescoreListwise(
        EvergreenDeepRetrievalCrossBorderListwiseRescoringProvider(query, candidates)
      ),
      RescoreListwise(AuthorBasedListwiseRescoringProvider(query, candidates)),
      RescoreListwise(ImpressedAuthorDecayRescoringProvider(query, candidates)),
      RescoreListwise(ImpressedMediaClusterBasedListwiseRescoringProvider(query, candidates)),
      RescoreListwise(ImpressedImageClusterBasedListwiseRescoringProvider(query, candidates)),
      RescoreListwise(CandidateSourceDiversityListwiseRescoringProvider(query, candidates)),
      RescoreListwise(GrokSlopScoreRescorer(query, candidates)),
      RescoreFeedbackFatigue(query),
      RescoreListwise(MultimodalEmbeddingRescorer(query, candidates)),
      RescoreLiveContent
    ) ++ ControlAiRescorer.allRescorers

    val usePhoenix = query.params(EnablePhoenixScorerParam) && query.params(EnablePhoenixScoreParam)

    val updatedScores = candidates.map { candidate =>
      val scoreOpt =
        if (usePhoenix) candidate.features.getOrElse(PhoenixScoreFeature, None)
        else candidate.features.getOrElse(ScoreFeature, None)

      val scaleFactor = rescorers.map(_(query, candidate)).product
      val updatedScore = scoreOpt.map { score =>
        if (score < Epsilon && noNegHeuristic) score else score * scaleFactor
      }
      FeatureMap(ScoreFeature, updatedScore)
    }

    Stitch.value(updatedScores)
  }
}
