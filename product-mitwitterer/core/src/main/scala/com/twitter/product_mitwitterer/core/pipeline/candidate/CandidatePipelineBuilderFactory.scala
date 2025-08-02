package com.twitter.product_mitwitterer.core.pipeline.candidate

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor.CandidateSourceEtwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.group_results_etwitterecutor.GroupResultsEtwitterecutor
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CandidatePipelineBuilderFactory @Inject() (
  queryFeatureHydratorEtwitterecutor: QueryFeatureHydratorEtwitterecutor,
  asyncFeatureMapEtwitterecutor: AsyncFeatureMapEtwitterecutor,
  candidateDecoratorEtwitterecutor: CandidateDecoratorEtwitterecutor,
  candidateFeatureHydratorEtwitterecutor: CandidateFeatureHydratorEtwitterecutor,
  candidateSourceEtwitterecutor: CandidateSourceEtwitterecutor,
  groupResultsEtwitterecutor: GroupResultsEtwitterecutor,
  filterEtwitterecutor: FilterEtwitterecutor,
  gateEtwitterecutor: GateEtwitterecutor,
  statsReceiver: StatsReceiver) {
  def get[
    Query <: PipelineQuery,
    CandidateSourceQuery,
    CandidateSourceResult,
    Result <: UniversalNoun[Any]
  ]: CandidatePipelineBuilder[
    Query,
    CandidateSourceQuery,
    CandidateSourceResult,
    Result
  ] = {
    new CandidatePipelineBuilder[
      Query,
      CandidateSourceQuery,
      CandidateSourceResult,
      Result
    ](
      queryFeatureHydratorEtwitterecutor,
      asyncFeatureMapEtwitterecutor,
      candidateDecoratorEtwitterecutor,
      candidateFeatureHydratorEtwitterecutor,
      candidateSourceEtwitterecutor,
      groupResultsEtwitterecutor,
      filterEtwitterecutor,
      gateEtwitterecutor,
      statsReceiver
    )
  }
}
