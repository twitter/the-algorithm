package com.twitter.product_mitwitterer.core.pipeline.candidate

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineResult
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutorResults
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor.CandidateSourceEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor

case class CandidatePipelineResult(
  candidateSourceIdentifier: CandidateSourceIdentifier,
  gateResult: Option[GateEtwitterecutorResult],
  queryFeatures: Option[QueryFeatureHydratorEtwitterecutor.Result],
  queryFeaturesPhase2: Option[QueryFeatureHydratorEtwitterecutor.Result],
  mergedAsyncQueryFeatures: Option[AsyncFeatureMap],
  candidateSourceResult: Option[CandidateSourceEtwitterecutorResult[UniversalNoun[Any]]],
  preFilterHydrationResult: Option[CandidateFeatureHydratorEtwitterecutorResult[UniversalNoun[Any]]],
  preFilterHydrationResultPhase2: Option[
    CandidateFeatureHydratorEtwitterecutorResult[UniversalNoun[Any]]
  ],
  filterResult: Option[FilterEtwitterecutorResult[UniversalNoun[Any]]],
  postFilterHydrationResult: Option[CandidateFeatureHydratorEtwitterecutorResult[UniversalNoun[Any]]],
  candidateDecoratorResult: Option[CandidateDecoratorEtwitterecutorResult],
  scorersResult: Option[CandidateFeatureHydratorEtwitterecutorResult[UniversalNoun[Any]]],
  asyncFeatureHydrationResults: Option[AsyncFeatureMapEtwitterecutorResults],
  failure: Option[PipelineFailure],
  result: Option[Seq[CandidateWithDetails]])
    etwittertends PipelineResult[Seq[CandidateWithDetails]] {

  override def withFailure(failure: PipelineFailure): CandidatePipelineResult =
    copy(failure = Some(failure))

  override def withResult(
    result: Seq[CandidateWithDetails]
  ): CandidatePipelineResult = copy(result = Some(result))

  override val resultSize: Int = result.map(PipelineResult.resultSize).getOrElse(0)
}

private[candidate] object IntermediateCandidatePipelineResult {
  def empty[Candidate <: UniversalNoun[Any]](
    candidateSourceIdentifier: CandidateSourceIdentifier
  ): IntermediateCandidatePipelineResult[Candidate] = {
    IntermediateCandidatePipelineResult(
      CandidatePipelineResult(
        candidateSourceIdentifier = candidateSourceIdentifier,
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
      ),
      None
    )
  }
}

private[candidate] case class IntermediateCandidatePipelineResult[Candidate <: UniversalNoun[Any]](
  underlyingResult: CandidatePipelineResult,
  featureMaps: Option[Map[Candidate, FeatureMap]])
    etwittertends PipelineResult[Seq[CandidateWithDetails]] {
  override val failure: Option[PipelineFailure] = underlyingResult.failure
  override val result: Option[Seq[CandidateWithDetails]] = underlyingResult.result

  override def withFailure(
    failure: PipelineFailure
  ): IntermediateCandidatePipelineResult[Candidate] =
    copy(underlyingResult = underlyingResult.withFailure(failure))

  override def withResult(
    result: Seq[CandidateWithDetails]
  ): IntermediateCandidatePipelineResult[Candidate] =
    copy(underlyingResult = underlyingResult.withResult(result))

  override def resultSize(): Int = underlyingResult.resultSize
}
