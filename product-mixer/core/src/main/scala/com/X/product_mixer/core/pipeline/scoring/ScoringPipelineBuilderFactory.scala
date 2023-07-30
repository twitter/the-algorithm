package com.X.product_mixer.core.pipeline.scoring

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.service.candidate_feature_hydrator_executor.CandidateFeatureHydratorExecutor
import com.X.product_mixer.core.service.gate_executor.GateExecutor
import com.X.product_mixer.core.service.selector_executor.SelectorExecutor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoringPipelineBuilderFactory @Inject() (
  gateExecutor: GateExecutor,
  selectorExecutor: SelectorExecutor,
  candidateFeatureHydratorExecutor: CandidateFeatureHydratorExecutor,
  statsReceiver: StatsReceiver) {

  def get[
    Query <: PipelineQuery,
    Candidate <: UniversalNoun[Any]
  ]: ScoringPipelineBuilder[Query, Candidate] = {
    new ScoringPipelineBuilder[Query, Candidate](
      gateExecutor,
      selectorExecutor,
      candidateFeatureHydratorExecutor,
      statsReceiver
    )
  }
}
