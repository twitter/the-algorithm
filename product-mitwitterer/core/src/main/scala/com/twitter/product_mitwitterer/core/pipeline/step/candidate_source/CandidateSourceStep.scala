package com.twitter.product_mitwitterer.core.pipeline.step.candidate_source

import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.transformer.BaseCandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithFeatures
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor.CandidateSourceEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor.CandidateSourceEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A candidate source step, which takes the query and gets csandidates from the candidate source.
 *
 * @param candidateSourceEtwitterecutor Candidate Source Etwitterecutor
 * @tparam Query Type of PipelineQuery domain model
 * @tparam Candidate Type of Candidates to filter
 * @tparam State The pipeline state domain model.
 */
case class CandidateSourceStep[
  Query <: PipelineQuery,
  CandidateSourceQuery,
  CandidateSourceResult,
  Candidate <: UniversalNoun[Any],
  State <: HasQuery[Query, State] with HasCandidatesWithFeatures[Candidate, State]] @Inject() (
  candidateSourceEtwitterecutor: CandidateSourceEtwitterecutor)
    etwittertends Step[
      State,
      CandidateSourceConfig[Query, CandidateSourceQuery, CandidateSourceResult, Candidate],
      Query,
      CandidateSourceEtwitterecutorResult[
        Candidate
      ]
    ] {
  override def isEmpty(
    config: CandidateSourceConfig[Query, CandidateSourceQuery, CandidateSourceResult, Candidate]
  ): Boolean = false

  override def adaptInput(
    state: State,
    config: CandidateSourceConfig[Query, CandidateSourceQuery, CandidateSourceResult, Candidate]
  ): Query = state.query

  override def arrow(
    config: CandidateSourceConfig[Query, CandidateSourceQuery, CandidateSourceResult, Candidate],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Query, CandidateSourceEtwitterecutorResult[Candidate]] = candidateSourceEtwitterecutor.arrow(
    config.candidateSource,
    config.queryTransformer,
    config.resultTransformer,
    config.resultFeaturesTransformers,
    contetwittert
  )

  override def updateState(
    state: State,
    etwitterecutorResult: CandidateSourceEtwitterecutorResult[Candidate],
    config: CandidateSourceConfig[Query, CandidateSourceQuery, CandidateSourceResult, Candidate]
  ): State = state
    .updateQuery(
      state.query
        .withFeatureMap(etwitterecutorResult.candidateSourceFeatureMap).asInstanceOf[
          Query]).updateCandidatesWithFeatures(etwitterecutorResult.candidates)
}

case class CandidateSourceConfig[
  Query <: PipelineQuery,
  CandidateSourceQuery,
  CandidateSourceResult,
  Candidate <: UniversalNoun[Any]
](
  candidateSource: BaseCandidateSource[CandidateSourceQuery, CandidateSourceResult],
  queryTransformer: BaseCandidatePipelineQueryTransformer[
    Query,
    CandidateSourceQuery
  ],
  resultTransformer: CandidatePipelineResultsTransformer[CandidateSourceResult, Candidate],
  resultFeaturesTransformers: Seq[CandidateFeatureTransformer[CandidateSourceResult]])
