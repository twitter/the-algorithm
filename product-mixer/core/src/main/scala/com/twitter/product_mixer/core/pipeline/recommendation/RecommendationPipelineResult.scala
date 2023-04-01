package com.twitter.product_mixer.core.pipeline.recommendation

import com.twitter.product_mixer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineResult
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.service.async_feature_map_executor.AsyncFeatureMapExecutorResults
import com.twitter.product_mixer.core.service.candidate_decorator_executor.CandidateDecoratorExecutorResult
import com.twitter.product_mixer.core.service.candidate_feature_hydrator_executor.CandidateFeatureHydratorExecutorResult
import com.twitter.product_mixer.core.service.candidate_pipeline_executor.CandidatePipelineExecutorResult
import com.twitter.product_mixer.core.service.domain_marshaller_executor.DomainMarshallerExecutor
import com.twitter.product_mixer.core.service.filter_executor.FilterExecutorResult
import com.twitter.product_mixer.core.service.gate_executor.GateExecutorResult
import com.twitter.product_mixer.core.service.pipeline_result_side_effect_executor.PipelineResultSideEffectExecutor
import com.twitter.product_mixer.core.service.quality_factor_executor.QualityFactorExecutorResult
import com.twitter.product_mixer.core.service.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
import com.twitter.product_mixer.core.service.scoring_pipeline_executor.ScoringPipelineExecutorResult
import com.twitter.product_mixer.core.service.selector_executor.SelectorExecutorResult
import com.twitter.product_mixer.core.service.transport_marshaller_executor.TransportMarshallerExecutor

case class RecommendationPipelineResult[Candidate <: UniversalNoun[Any], ResultType](
  qualityFactorResult: Option[QualityFactorExecutorResult],
  gateResult: Option[GateExecutorResult],
  queryFeatures: Option[QueryFeatureHydratorExecutor.Result],
  queryFeaturesPhase2: Option[QueryFeatureHydratorExecutor.Result],
  mergedAsyncQueryFeatures: Option[AsyncFeatureMap],
  candidatePipelineResults: Option[CandidatePipelineExecutorResult],
  dependentCandidatePipelineResults: Option[CandidatePipelineExecutorResult],
  postCandidatePipelinesSelectorResults: Option[SelectorExecutorResult],
  postCandidatePipelinesFeatureHydrationResults: Option[
    CandidateFeatureHydratorExecutorResult[Candidate]
  ],
  globalFilterResults: Option[FilterExecutorResult[Candidate]],
  scoringPipelineResults: Option[ScoringPipelineExecutorResult[Candidate]],
  resultSelectorResults: Option[SelectorExecutorResult],
  postSelectionFilterResults: Option[FilterExecutorResult[Candidate]],
  candidateDecoratorResult: Option[CandidateDecoratorExecutorResult],
  domainMarshallerResults: Option[DomainMarshallerExecutor.Result[HasMarshalling]],
  resultSideEffectResults: Option[PipelineResultSideEffectExecutor.Result],
  asyncFeatureHydrationResults: Option[AsyncFeatureMapExecutorResults],
  transportMarshallerResults: Option[TransportMarshallerExecutor.Result[ResultType]],
  failure: Option[PipelineFailure],
  result: Option[ResultType])
    extends PipelineResult[ResultType] {
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
