package com.twitter.product_mitwitterer.core.pipeline.mitwitterer

import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineResult
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutorResults
import com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor.CandidatePipelineEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor.PipelineResultSideEffectEtwitterecutor
import com.twitter.product_mitwitterer.core.service.quality_factor_etwitterecutor.QualityFactorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor

/**
 * A [[MitwittererPipelineResult]] includes both the user-visible [[PipelineResult]] and all the
 * Etwitterecution details possible - intermediate results, what components did, etc.
 */
case class MitwittererPipelineResult[Result](
  qualityFactorResult: Option[QualityFactorEtwitterecutorResult],
  gateResult: Option[GateEtwitterecutorResult],
  queryFeatures: Option[QueryFeatureHydratorEtwitterecutor.Result],
  queryFeaturesPhase2: Option[QueryFeatureHydratorEtwitterecutor.Result],
  mergedAsyncQueryFeatures: Option[AsyncFeatureMap],
  candidatePipelineResults: Option[CandidatePipelineEtwitterecutorResult],
  dependentCandidatePipelineResults: Option[CandidatePipelineEtwitterecutorResult],
  resultSelectorResults: Option[SelectorEtwitterecutorResult],
  domainMarshallerResults: Option[DomainMarshallerEtwitterecutor.Result[HasMarshalling]],
  resultSideEffectResults: Option[PipelineResultSideEffectEtwitterecutor.Result],
  asyncFeatureHydrationResults: Option[AsyncFeatureMapEtwitterecutorResults],
  transportMarshallerResults: Option[TransportMarshallerEtwitterecutor.Result[Result]],
  failure: Option[PipelineFailure],
  result: Option[Result])
    etwittertends PipelineResult[Result] {

  override def withFailure(failure: PipelineFailure): PipelineResult[Result] =
    copy(failure = Some(failure))

  override def withResult(result: Result): PipelineResult[Result] = copy(result = Some(result))

  /**
   * resultSize is calculated based on the selector results rather than the marshalled results. The
   * structure of the marshalled format is unknown, making operating on selector results more
   * convenient. This will implicitly etwittercluded cursors built during marshalling but cursors don't
   * contribute to the result size anyway.
   */
  override val resultSize: Int =
    resultSelectorResults.map(_.selectedCandidates).map(PipelineResult.resultSize).getOrElse(0)
}

object MitwittererPipelineResult {
  def empty[A]: MitwittererPipelineResult[A] = MitwittererPipelineResult(
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
