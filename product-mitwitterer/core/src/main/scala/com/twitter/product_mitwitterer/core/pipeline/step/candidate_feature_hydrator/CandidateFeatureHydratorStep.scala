package com.twitter.product_mitwitterer.core.pipeline.step.candidate_feature_hydrator

import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
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
 * A candidate level feature hydration step, it takes the input list of candidates and the given
 * hydrators and etwitterecutes them. The [[State]] object is responsible for merging the resulting
 * feature maps with the hydrated ones in its updateCandidatesWithFeatures.
 *
 * @param candidateFeatureHydratorEtwitterecutor Hydrator Etwitterecutor
 * @tparam Query Type of PipelineQuery domain model
 * @tparam Candidate Type of Candidates to hydrate features for.
 * @tparam State The pipeline state domain model.
 */
case class CandidateFeatureHydratorStep[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any],
  State <: HasQuery[Query, State] with HasCandidatesWithFeatures[
    Candidate,
    State
  ]] @Inject() (
  candidateFeatureHydratorEtwitterecutor: CandidateFeatureHydratorEtwitterecutor)
    etwittertends Step[State, Seq[
      BaseCandidateFeatureHydrator[Query, Candidate, _]
    ], CandidateFeatureHydratorEtwitterecutor.Inputs[
      Query,
      Candidate
    ], CandidateFeatureHydratorEtwitterecutorResult[Candidate]] {

  override def adaptInput(
    state: State,
    config: Seq[BaseCandidateFeatureHydrator[Query, Candidate, _]]
  ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate] =
    CandidateFeatureHydratorEtwitterecutor.Inputs(state.query, state.candidatesWithFeatures)

  override def arrow(
    config: Seq[BaseCandidateFeatureHydrator[Query, Candidate, _]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[
    CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
    CandidateFeatureHydratorEtwitterecutorResult[Candidate]
  ] = candidateFeatureHydratorEtwitterecutor.arrow(config, contetwittert)

  override def updateState(
    input: State,
    etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Candidate],
    config: Seq[BaseCandidateFeatureHydrator[Query, Candidate, _]]
  ): State = {
    val candidatesWithHydratedFeatures = etwitterecutorResult.results
    if (candidatesWithHydratedFeatures.isEmpty) {
      input
    } else {
      input.updateCandidatesWithFeatures(candidatesWithHydratedFeatures)
    }
  }

  override def isEmpty(
    config: Seq[BaseCandidateFeatureHydrator[Query, Candidate, _]]
  ): Boolean =
    config.isEmpty
}
