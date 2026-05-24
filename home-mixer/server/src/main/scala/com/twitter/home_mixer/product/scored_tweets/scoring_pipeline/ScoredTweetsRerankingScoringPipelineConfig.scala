package com.twitter.home_mixer.product.scored_tweets.scoring_pipeline

import com.twitter.home_mixer.functional_component.scorer.PhoenixModelRerankingScorer
import com.twitter.home_mixer.functional_component.scorer.WeighedModelRerankingScorer
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePhoenixScorerParam
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.scorer.param_gated.ParamGatedScorer
import com.twitter.product_mixer.core.functional_component.gate.BaseGate
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsRerankingScoringPipelineConfig @Inject() (
  // rerankers
  weighedModelRerankingScorer: WeighedModelRerankingScorer,
  phoenixModelRerankingScorer: PhoenixModelRerankingScorer,
  // Base scoring pipeline config
  scoredTweetsModelScoringPipelineConfig: ScoredTweetsModelScoringPipelineConfig)
    extends ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScoringPipelineIdentifier =
    ScoringPipelineIdentifier("ScoredTweetsReranking")

  override val gates: Seq[BaseGate[ScoredTweetsQuery]] =
    scoredTweetsModelScoringPipelineConfig.gates

  override val selectors: Seq[Selector[ScoredTweetsQuery]] =
    scoredTweetsModelScoringPipelineConfig.selectors

  override val scorers: Seq[Scorer[ScoredTweetsQuery, TweetCandidate]] =
    Seq(
      weighedModelRerankingScorer,
      ParamGatedScorer(EnablePhoenixScorerParam, phoenixModelRerankingScorer)
    )
}
