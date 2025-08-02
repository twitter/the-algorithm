package com.twitter.product_mitwitterer.core.pipeline.step.scorer

import com.twitter.product_mitwitterer.core.functional_component.scorer.Scorer
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithFeatures
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A scoring step, it takes the input list of candidates and the given
 * scorers and etwitterecutes them. The [[State]] object is responsible for merging the resulting
 * feature maps with the scored ones in its updateCandidatesWithFeatures.
 *
 * @param candidateFeatureHydratorEtwitterecutor Hydrator Etwitterecutor
 * @tparam Query Type of PipelineQuery domain model
 * @tparam Candidate Type of Candidates to hydrate features for.
 * @tparam State The pipeline state domain model.
 */
case class ScorerStep[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any],
  State <: HasQuery[Query, State] with HasCandidatesWithFeatures[
    Candidate,
    State
  ]] @Inject() (
  candidateFeatureHydratorEtwitterecutor: CandidateFeatureHydratorEtwitterecutor)
    etwittertends Step[State, Seq[
      Scorer[Query, Candidate]
    ], CandidateFeatureHydratorEtwitterecutor.Inputs[
      Query,
      Candidate
    ], CandidateFeatureHydratorEtwitterecutorResult[Candidate]] {

  override def adaptInput(
    state: State,
    config: Seq[Scorer[Query, Candidate]]
  ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate] =
    CandidateFeatureHydratorEtwitterecutor.Inputs(state.query, state.candidatesWithFeatures)

  override def arrow(
    config: Seq[Scorer[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[
    CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
    CandidateFeatureHydratorEtwitterecutorResult[Candidate]
  ] = candidateFeatureHydratorEtwitterecutor.arrow(config, contetwittert)

  override def updateState(
    input: State,
    etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Candidate],
    config: Seq[Scorer[Query, Candidate]]
  ): State = {
    val resultCandidates = etwitterecutorResult.results
    if (resultCandidates.isEmpty) {
      input
    } else {
      input.updateCandidatesWithFeatures(resultCandidates)
    }
  }

  override def isEmpty(config: Seq[Scorer[Query, Candidate]]): Boolean =
    config.isEmpty
}
