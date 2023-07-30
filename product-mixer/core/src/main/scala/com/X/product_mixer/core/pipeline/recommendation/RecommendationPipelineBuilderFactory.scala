package com.X.product_mixer.core.pipeline.recommendation

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.HasMarshalling
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineBuilderFactory
import com.X.product_mixer.core.pipeline.scoring.ScoringPipelineBuilderFactory
import com.X.product_mixer.core.service.candidate_decorator_executor.CandidateDecoratorExecutor
import com.X.product_mixer.core.service.candidate_feature_hydrator_executor.CandidateFeatureHydratorExecutor
import com.X.product_mixer.core.service.candidate_pipeline_executor.CandidatePipelineExecutor
import com.X.product_mixer.core.service.domain_marshaller_executor.DomainMarshallerExecutor
import com.X.product_mixer.core.service.filter_executor.FilterExecutor
import com.X.product_mixer.core.service.gate_executor.GateExecutor
import com.X.product_mixer.core.service.pipeline_result_side_effect_executor.PipelineResultSideEffectExecutor
import com.X.product_mixer.core.service.async_feature_map_executor.AsyncFeatureMapExecutor
import com.X.product_mixer.core.service.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
import com.X.product_mixer.core.service.scoring_pipeline_executor.ScoringPipelineExecutor
import com.X.product_mixer.core.service.selector_executor.SelectorExecutor
import com.X.product_mixer.core.service.transport_marshaller_executor.TransportMarshallerExecutor

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationPipelineBuilderFactory @Inject() (
  candidatePipelineExecutor: CandidatePipelineExecutor,
  gateExecutor: GateExecutor,
  selectorExecutor: SelectorExecutor,
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  candidateFeatureHydratorExecutor: CandidateFeatureHydratorExecutor,
  filterExecutor: FilterExecutor,
  scoringPipelineExecutor: ScoringPipelineExecutor,
  candidateDecoratorExecutor: CandidateDecoratorExecutor,
  domainMarshallerExecutor: DomainMarshallerExecutor,
  transportMarshallerExecutor: TransportMarshallerExecutor,
  pipelineResultSideEffectExecutor: PipelineResultSideEffectExecutor,
  candidatePipelineBuilderFactory: CandidatePipelineBuilderFactory,
  scoringPipelineBuilderFactory: ScoringPipelineBuilderFactory,
  statsReceiver: StatsReceiver) {

  def get[
    Query <: PipelineQuery,
    Candidate <: UniversalNoun[Any],
    DomainResultType <: HasMarshalling,
    Result
  ]: RecommendationPipelineBuilder[Query, Candidate, DomainResultType, Result] = {
    new RecommendationPipelineBuilder[Query, Candidate, DomainResultType, Result](
      candidatePipelineExecutor,
      gateExecutor,
      selectorExecutor,
      queryFeatureHydratorExecutor,
      asyncFeatureMapExecutor,
      candidateFeatureHydratorExecutor,
      filterExecutor,
      scoringPipelineExecutor,
      candidateDecoratorExecutor,
      domainMarshallerExecutor,
      transportMarshallerExecutor,
      pipelineResultSideEffectExecutor,
      candidatePipelineBuilderFactory,
      scoringPipelineBuilderFactory,
      statsReceiver
    )
  }
}
