package com.twitter.tweet_mixer.scoring_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.HydraScoringPipelineEnabled
import com.twitter.tweet_mixer.scorer.HydraScorer

case class HydraScoringPipelineConfig(
  hydraScorer: HydraScorer,
  excludeDeepRetrievalCandidatePipelineIdentifiers: Set[CandidatePipelineIdentifier] = Set.empty,
) extends ScoringPipelineConfig[PipelineQuery, TweetCandidate] {

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(HydraScoringPipelineEnabled)

  override val identifier: ScoringPipelineIdentifier =
    ScoringPipelineIdentifier("HydraScoring")

  override val selectors: Seq[Selector[PipelineQuery]] = Seq(InsertAppendResults(AllPipelines))

  override val scorers: Seq[Scorer[PipelineQuery, TweetCandidate]] = Seq(hydraScorer)
}
