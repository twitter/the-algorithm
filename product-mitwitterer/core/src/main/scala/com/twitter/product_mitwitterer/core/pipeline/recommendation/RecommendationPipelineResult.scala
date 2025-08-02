package com.twitter.product_mitwitterer.core.pipeline.recommendation

import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineResult
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutorResults
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor.CandidatePipelineEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor.PipelineResultSideEffectEtwitterecutor
import com.twitter.product_mitwitterer.core.service.quality_factor_etwitterecutor.QualityFactorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.scoring_pipeline_etwitterecutor.ScoringPipelineEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor

case class RecommendationPipelineResult[Candidate <: UniversalNoun[Any], ResultType](
  qualityFactorResult: Option[QualityFactorEtwitterecutorResult],
  gateResult: Option[GateEtwitterecutorResult],
  queryFeatures: Option[QueryFeatureHydratorEtwitterecutor.Result],
  queryFeaturesPhase2: Option[QueryFeatureHydratorEtwitterecutor.Result],
  mergedAsyncQueryFeatures: Option[AsyncFeatureMap],
  candidatePipelineResults: Option[CandidatePipelineEtwitterecutorResult],
  dependentCandidatePipelineResults: Option[CandidatePipelineEtwitterecutorResult],
  postCandidatePipelinesSelectorResults: Option[SelectorEtwitterecutorResult],
  postCandidatePipelinesFeatureHydrationResults: Option[
    CandidateFeatureHydratorEtwitterecutorResult[Candidate]
  ],
  globalFilterResults: Option[FilterEtwitterecutorResult[Candidate]],
  scoringPipelineResults: Option[ScoringPipelineEtwitterecutorResult[Candidate]],
  resultSelectorResults: Option[SelectorEtwitterecutorResult],
  postSelectionFilterResults: Option[FilterEtwitterecutorResult[Candidate]],
  candidateDecoratorResult: Option[CandidateDecoratorEtwitterecutorResult],
  domainMarshallerResults: Option[DomainMarshallerEtwitterecutor.Result[HasMarshalling]],
  resultSideEffectResults: Option[PipelineResultSideEffectEtwitterecutor.Result],
  asyncFeatureHydrationResults: Option[AsyncFeatureMapEtwitterecutorResults],
  transportMarshallerResults: Option[TransportMarshallerEtwitterecutor.Result[ResultType]],
  failure: Option[PipelineFailure],
  result: Option[ResultType])
    etwittertends PipelineResult[ResultType] {
  override val resultSize: Int = result match {
    case Some(seqResult @ Seq(_)) => seqResult.length
    case Some(_) => 1
    case None => 0
  }

  override def withFailure(
    failure: PipelineFailure
  ): RecommendationPipelineResult[Candidate, ResultType] =
    copy(failure = Some(failure))
  override def withResult(result: ResultType): RecommendationPipelineResult[Candidate, ResultType] =
    copy(result = Some(result))
}

object RecommendationPipelineResult {
  def empty[A <: UniversalNoun[Any], B]: RecommendationPipelineResult[A, B] =
    RecommendationPipelineResult(
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
      None,
      None,
      None,
      None,
      None,
      None,
      None
    )
}
