package com.twitter.product_mixer.core.pipeline.mixer

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineBuilderFactory
import com.twitter.product_mixer.core.service.candidate_pipeline_executor.CandidatePipelineExecutor
import com.twitter.product_mixer.core.service.domain_marshaller_executor.DomainMarshallerExecutor
import com.twitter.product_mixer.core.service.gate_executor.GateExecutor
import com.twitter.product_mixer.core.service.pipeline_result_side_effect_executor.PipelineResultSideEffectExecutor
import com.twitter.product_mixer.core.service.async_feature_map_executor.AsyncFeatureMapExecutor
import com.twitter.product_mixer.core.service.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
import com.twitter.product_mixer.core.service.selector_executor.SelectorExecutor
import com.twitter.product_mixer.core.service.transport_marshaller_executor.TransportMarshallerExecutor

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MixerPipelineBuilderFactory @Inject() (
  candidatePipelineExecutor: CandidatePipelineExecutor,
  gateExecutor: GateExecutor,
  selectorExecutor: SelectorExecutor,
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  domainMarshallerExecutor: DomainMarshallerExecutor,
  transportMarshallerExecutor: TransportMarshallerExecutor,
  pipelineResultSideEffectExecutor: PipelineResultSideEffectExecutor,
  candidatePipelineBuilderFactory: CandidatePipelineBuilderFactory,
  statsReceiver: StatsReceiver) {
  def get[
    Query <: PipelineQuery,
    DomainResultType <: HasMarshalling,
    Result
  ]: MixerPipelineBuilder[Query, DomainResultType, Result] = {
    new MixerPipelineBuilder[Query, DomainResultType, Result](
      candidatePipelineExecutor,
      gateExecutor,
      selectorExecutor,
      queryFeatureHydratorExecutor,
      asyncFeatureMapExecutor,
      domainMarshallerExecutor,
      transportMarshallerExecutor,
      pipelineResultSideEffectExecutor,
      candidatePipelineBuilderFactory,
      statsReceiver
    )
  }
}
