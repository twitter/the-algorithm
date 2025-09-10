package com.twitter.home_mixer.product.scored_tweets.scoring_pipeline

import com.twitter.home_mixer.product.scored_tweets.candidate_pipeline.ScoredTweetsStaticCandidatePipelineConfig
import com.twitter.home_mixer.product.scored_tweets.gate.DenyLowSignalUserGate
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam
import com.twitter.home_mixer.product.scored_tweets.scorer.HeuristicScorer
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.core.functional_component.common.AllExceptPipelines
import com.twitter.product_mixer.core.functional_component.gate.BaseGate
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import com.twitter.timelines.configapi.FSParam

object ScoredTweetsHeuristicScoringPipelineConfig
    extends ScoringPipelineConfig[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScoringPipelineIdentifier =
    ScoringPipelineIdentifier("ScoredTweetsHeuristic")

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(ScoredTweetsParam.EnableHeuristicScoringPipeline)

  override val gates: Seq[BaseGate[ScoredTweetsQuery]] = Seq(DenyLowSignalUserGate)

  private val allExcept = AllExceptPipelines(
    pipelinesToExclude = Set(ScoredTweetsStaticCandidatePipelineConfig.Identifier)
  )

  override val selectors: Seq[Selector[ScoredTweetsQuery]] = Seq(InsertAppendResults(allExcept))

  override val scorers: Seq[Scorer[ScoredTweetsQuery, TweetCandidate]] =
    Seq(HeuristicScorer)
}
