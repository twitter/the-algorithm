package com.twitter.product_mitwitterer.core.pipeline.recommendation

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineBuilderFactory
import com.twitter.product_mitwitterer.core.pipeline.scoring.ScoringPipelineBuilderFactory
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor.CandidatePipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor.PipelineResultSideEffectEtwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutor
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.scoring_pipeline_etwitterecutor.ScoringPipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RecommendationPipelineBuilderFactory @Inject() (
  candidatePipelineEtwitterecutor: CandidatePipelineEtwitterecutor,
  gateEtwitterecutor: GateEtwitterecutor,
  selectorEtwitterecutor: SelectorEtwitterecutor,
  queryFeatureHydratorEtwitterecutor: QueryFeatureHydratorEtwitterecutor,
  asyncFeatureMapEtwitterecutor: AsyncFeatureMapEtwitterecutor,
  candidateFeatureHydratorEtwitterecutor: CandidateFeatureHydratorEtwitterecutor,
  filterEtwitterecutor: FilterEtwitterecutor,
  scoringPipelineEtwitterecutor: ScoringPipelineEtwitterecutor,
  candidateDecoratorEtwitterecutor: CandidateDecoratorEtwitterecutor,
  domainMarshallerEtwitterecutor: DomainMarshallerEtwitterecutor,
  transportMarshallerEtwitterecutor: TransportMarshallerEtwitterecutor,
  pipelineResultSideEffectEtwitterecutor: PipelineResultSideEffectEtwitterecutor,
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
      candidatePipelineEtwitterecutor,
      gateEtwitterecutor,
      selectorEtwitterecutor,
      queryFeatureHydratorEtwitterecutor,
      asyncFeatureMapEtwitterecutor,
      candidateFeatureHydratorEtwitterecutor,
      filterEtwitterecutor,
      scoringPipelineEtwitterecutor,
      candidateDecoratorEtwitterecutor,
      domainMarshallerEtwitterecutor,
      transportMarshallerEtwitterecutor,
      pipelineResultSideEffectEtwitterecutor,
      candidatePipelineBuilderFactory,
      scoringPipelineBuilderFactory,
      statsReceiver
    )
  }
}
