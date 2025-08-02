package com.twitter.product_mitwitterer.core.pipeline.mitwitterer

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineBuilderFactory
import com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor.CandidatePipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor.PipelineResultSideEffectEtwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutor
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class MitwittererPipelineBuilderFactory @Inject() (
  candidatePipelineEtwitterecutor: CandidatePipelineEtwitterecutor,
  gateEtwitterecutor: GateEtwitterecutor,
  selectorEtwitterecutor: SelectorEtwitterecutor,
  queryFeatureHydratorEtwitterecutor: QueryFeatureHydratorEtwitterecutor,
  asyncFeatureMapEtwitterecutor: AsyncFeatureMapEtwitterecutor,
  domainMarshallerEtwitterecutor: DomainMarshallerEtwitterecutor,
  transportMarshallerEtwitterecutor: TransportMarshallerEtwitterecutor,
  pipelineResultSideEffectEtwitterecutor: PipelineResultSideEffectEtwitterecutor,
  candidatePipelineBuilderFactory: CandidatePipelineBuilderFactory,
  statsReceiver: StatsReceiver) {
  def get[
    Query <: PipelineQuery,
    DomainResultType <: HasMarshalling,
    Result
  ]: MitwittererPipelineBuilder[Query, DomainResultType, Result] = {
    new MitwittererPipelineBuilder[Query, DomainResultType, Result](
      candidatePipelineEtwitterecutor,
      gateEtwitterecutor,
      selectorEtwitterecutor,
      queryFeatureHydratorEtwitterecutor,
      asyncFeatureMapEtwitterecutor,
      domainMarshallerEtwitterecutor,
      transportMarshallerEtwitterecutor,
      pipelineResultSideEffectEtwitterecutor,
      candidatePipelineBuilderFactory,
      statsReceiver
    )
  }
}
