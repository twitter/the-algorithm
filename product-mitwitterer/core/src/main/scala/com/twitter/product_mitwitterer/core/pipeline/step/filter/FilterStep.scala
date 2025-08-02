package com.twitter.product_mitwitterer.core.pipeline.step.filter

import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithFeatures
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A candidate filter step, it takes the input list of candidates and the given filter and applies
 * the filters on the candidates in sequence, returning the final kept candidates list to State.
 *
 * @param filterEtwitterecutor Filter Etwitterecutor
 * @tparam Query Type of PipelineQuery domain model
 * @tparam Candidate Type of Candidates to filter
 * @tparam State The pipeline state domain model.
 */
case class FilterStep[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any],
  State <: HasQuery[Query, State] with HasCandidatesWithFeatures[
    Candidate,
    State
  ]] @Inject() (filterEtwitterecutor: FilterEtwitterecutor)
    etwittertends Step[State, Seq[
      Filter[Query, Candidate]
    ], (Query, Seq[CandidateWithFeatures[Candidate]]), FilterEtwitterecutorResult[Candidate]] {

  override def isEmpty(config: Seq[Filter[Query, Candidate]]): Boolean = config.isEmpty

  override def adaptInput(
    state: State,
    config: Seq[Filter[Query, Candidate]]
  ): (Query, Seq[CandidateWithFeatures[Candidate]]) =
    (state.query, state.candidatesWithFeatures)

  override def arrow(
    config: Seq[Filter[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[(Query, Seq[CandidateWithFeatures[Candidate]]), FilterEtwitterecutorResult[Candidate]] =
    filterEtwitterecutor.arrow(config, contetwittert)

  override def updateState(
    state: State,
    etwitterecutorResult: FilterEtwitterecutorResult[Candidate],
    config: Seq[Filter[Query, Candidate]]
  ): State = {
    val keptCandidates = etwitterecutorResult.result
    val candidatesMap = state.candidatesWithFeatures.map { candidatesWithFeatures =>
      candidatesWithFeatures.candidate -> candidatesWithFeatures
    }.toMap
    val newCandidates = keptCandidates.flatMap { candidate =>
      candidatesMap.get(candidate)
    }
    state.updateCandidatesWithFeatures(newCandidates)
  }
}
