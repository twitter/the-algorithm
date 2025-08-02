package com.twitter.product_mitwitterer.core.pipeline.step.decorator

import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithDetails
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithFeatures
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A candidate decoration step, which takes the query and candidates and outputs decorations for them
 *
 * @param candidateDecoratorEtwitterecutor Candidate Source Etwitterecutor
 * @tparam Query Type of PipelineQuery domain model
 * @tparam Candidate Type of Candidates to filter
 * @tparam State The pipeline state domain model.
 */
case class DecoratorStep[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any],
  State <: HasQuery[Query, State] with HasCandidatesWithDetails[
    State
  ] with HasCandidatesWithFeatures[
    Candidate,
    State
  ]] @Inject() (candidateDecoratorEtwitterecutor: CandidateDecoratorEtwitterecutor)
    etwittertends Step[
      State,
      Option[CandidateDecorator[Query, Candidate]],
      (Query, Seq[CandidateWithFeatures[Candidate]]),
      CandidateDecoratorEtwitterecutorResult
    ] {

  override def isEmpty(config: Option[CandidateDecorator[Query, Candidate]]): Boolean =
    config.isEmpty

  override def adaptInput(
    state: State,
    config: Option[CandidateDecorator[Query, Candidate]]
  ): (Query, Seq[CandidateWithFeatures[Candidate]]) =
    (state.query, state.candidatesWithFeatures)

  override def arrow(
    config: Option[CandidateDecorator[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[(Query, Seq[CandidateWithFeatures[Candidate]]), CandidateDecoratorEtwitterecutorResult] =
    candidateDecoratorEtwitterecutor.arrow(config, contetwittert)

  override def updateState(
    state: State,
    etwitterecutorResult: CandidateDecoratorEtwitterecutorResult,
    config: Option[CandidateDecorator[Query, Candidate]]
  ): State = {
    state.updateDecorations(etwitterecutorResult.result)
  }
}
