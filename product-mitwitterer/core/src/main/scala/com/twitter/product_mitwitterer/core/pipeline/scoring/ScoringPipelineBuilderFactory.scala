package com.twitter.product_mitwitterer.core.pipeline.scoring

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutor
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ScoringPipelineBuilderFactory @Inject() (
  gateEtwitterecutor: GateEtwitterecutor,
  selectorEtwitterecutor: SelectorEtwitterecutor,
  candidateFeatureHydratorEtwitterecutor: CandidateFeatureHydratorEtwitterecutor,
  statsReceiver: StatsReceiver) {

  def get[
    Query <: PipelineQuery,
    Candidate <: UniversalNoun[Any]
  ]: ScoringPipelineBuilder[Query, Candidate] = {
    new ScoringPipelineBuilder[Query, Candidate](
      gateEtwitterecutor,
      selectorEtwitterecutor,
      candidateFeatureHydratorEtwitterecutor,
      statsReceiver
    )
  }
}
