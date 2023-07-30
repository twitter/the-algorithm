package com.X.product_mixer.core.pipeline.candidate

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.service.async_feature_map_executor.AsyncFeatureMapExecutor
import com.X.product_mixer.core.service.candidate_decorator_executor.CandidateDecoratorExecutor
import com.X.product_mixer.core.service.candidate_feature_hydrator_executor.CandidateFeatureHydratorExecutor
import com.X.product_mixer.core.service.candidate_source_executor.CandidateSourceExecutor
import com.X.product_mixer.core.service.filter_executor.FilterExecutor
import com.X.product_mixer.core.service.gate_executor.GateExecutor
import com.X.product_mixer.core.service.group_results_executor.GroupResultsExecutor
import com.X.product_mixer.core.service.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CandidatePipelineBuilderFactory @Inject() (
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  candidateDecoratorExecutor: CandidateDecoratorExecutor,
  candidateFeatureHydratorExecutor: CandidateFeatureHydratorExecutor,
  candidateSourceExecutor: CandidateSourceExecutor,
  groupResultsExecutor: GroupResultsExecutor,
  filterExecutor: FilterExecutor,
  gateExecutor: GateExecutor,
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
      queryFeatureHydratorExecutor,
      asyncFeatureMapExecutor,
      candidateDecoratorExecutor,
      candidateFeatureHydratorExecutor,
      candidateSourceExecutor,
      groupResultsExecutor,
      filterExecutor,
      gateExecutor,
      statsReceiver
    )
  }
}
