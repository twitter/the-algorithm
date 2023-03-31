package com.twitter.product_mixer.core.pipeline.mixer

import com.twitter.product_mixer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineResult
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.service.async_feature_map_executor.AsyncFeatureMapExecutorResults
import com.twitter.product_mixer.core.service.candidate_pipeline_executor.CandidatePipelineExecutorResult
import com.twitter.product_mixer.core.service.domain_marshaller_executor.DomainMarshallerExecutor
import com.twitter.product_mixer.core.service.gate_executor.GateExecutorResult
import com.twitter.product_mixer.core.service.pipeline_result_side_effect_executor.PipelineResultSideEffectExecutor
import com.twitter.product_mixer.core.service.quality_factor_executor.QualityFactorExecutorResult
import com.twitter.product_mixer.core.service.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
import com.twitter.product_mixer.core.service.selector_executor.SelectorExecutorResult
import com.twitter.product_mixer.core.service.transport_marshaller_executor.TransportMarshallerExecutor

/**
 * A [[MixerPipelineResult]] includes both the user-visible [[PipelineResult]] and all the
 * Execution details possible - intermediate results, what components did, etc.
 */
case class MixerPipelineResult[Result](
  qualityFactorResult: Option[QualityFactorExecutorResult],
  gateResult: Option[GateExecutorResult],
  queryFeatures: Option[QueryFeatureHydratorExecutor.Result],
  queryFeaturesPhase2: Option[QueryFeatureHydratorExecutor.Result],
  mergedAsyncQueryFeatures: Option[AsyncFeatureMap],
  candidatePipelineResults: Option[CandidatePipelineExecutorResult],
  dependentCandidatePipelineResults: Option[CandidatePipelineExecutorResult],
  resultSelectorResults: Option[SelectorExecutorResult],
  domainMarshallerResults: Option[DomainMarshallerExecutor.Result[HasMarshalling]],
  resultSideEffectResults: Option[PipelineResultSideEffectExecutor.Result],
  asyncFeatureHydrationResults: Option[AsyncFeatureMapExecutorResults],
  transportMarshallerResults: Option[TransportMarshallerExecutor.Result[Result]],
  failure: Option[PipelineFailure],
  result: Option[Result])
    extends PipelineResult[Result] {

  override def withFailure(failure: PipelineFailure): PipelineResult[Result] =
    copy(failure = Some(failure))

  override def withResult(result: Result): PipelineResult[Result] = copy(result = Some(result))

  /**
   * resultSize is calculated based on the selector results rather than the marshalled results. The
   * structure of the marshalled format is unknown, making operating on selector results more
   * convenient. This will implicitly excluded cursors built during marshalling but cursors don't
   * contribute to the result size anyway.
   */
  override val resultSize: Int =
    resultSelectorResults.map(_.selectedCandidates).map(PipelineResult.resultSize).getOrElse(0)
}

object MixerPipelineResult {
  def empty[A]: MixerPipelineResult[A] = MixerPipelineResult(
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None
  )
}
