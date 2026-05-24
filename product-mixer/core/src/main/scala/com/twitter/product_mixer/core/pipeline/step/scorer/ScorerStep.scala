package com.twitter.product_mixer.core.pipeline.step.scorer

import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.state.HasCandidatesWithFeatures
import com.twitter.product_mixer.core.pipeline.state.HasQuery
import com.twitter.product_mixer.core.pipeline.step.Step
import com.twitter.product_mixer.core.service.Executor
import com.twitter.product_mixer.core.service.candidate_feature_hydrator_executor.CandidateFeatureHydratorExecutor
import com.twitter.product_mixer.core.service.candidate_feature_hydrator_executor.CandidateFeatureHydratorExecutorResult
import com.twitter.stitch.Arrow
import javax.inject.Inject

/**
 * A scoring step, it takes the input list of candidates and the given
 * scorers and executes them. The [[State]] object is responsible for merging the resulting
 * feature maps with the scored ones in its updateCandidatesWithFeatures.
 *
 * @param candidateFeatureHydratorExecutor Hydrator Executor
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
  candidateFeatureHydratorExecutor: CandidateFeatureHydratorExecutor)
    extends Step[State, Seq[
      Scorer[Query, Candidate]
    ], CandidateFeatureHydratorExecutor.Inputs[
      Query,
      Candidate
    ], CandidateFeatureHydratorExecutorResult[Candidate]] {

  override def adaptInput(
    state: State,
    config: Seq[Scorer[Query, Candidate]]
  ): CandidateFeatureHydratorExecutor.Inputs[Query, Candidate] =
    CandidateFeatureHydratorExecutor.Inputs(state.query, state.candidatesWithFeatures)

  override def arrow(
    config: Seq[Scorer[Query, Candidate]],
    context: Executor.Context
  ): Arrow[
    CandidateFeatureHydratorExecutor.Inputs[Query, Candidate],
    CandidateFeatureHydratorExecutorResult[Candidate]
  ] = candidateFeatureHydratorExecutor.arrow(config, context)

  override def updateState(
    input: State,
    executorResult: CandidateFeatureHydratorExecutorResult[Candidate],
    config: Seq[Scorer[Query, Candidate]]
  ): State = {
    val resultCandidates = executorResult.results
    if (resultCandidates.isEmpty) {
      input
    } else {
      input.updateCandidatesWithFeatures(resultCandidates)
    }
  }

  override def isEmpty(config: Seq[Scorer[Query, Candidate]]): Boolean =
    config.isEmpty
}
