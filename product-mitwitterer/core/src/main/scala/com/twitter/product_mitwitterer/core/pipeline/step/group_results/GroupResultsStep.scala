package com.twitter.product_mitwitterer.core.pipeline.step.group_results

import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithDetails
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithFeatures
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.group_results_etwitterecutor.GroupResultsEtwitterecutor
import com.twitter.product_mitwitterer.core.service.group_results_etwitterecutor.GroupResultsEtwitterecutorInput
import com.twitter.product_mitwitterer.core.service.group_results_etwitterecutor.GroupResultsEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A group results step, it takes the input list of candidates and decorations, and assembles
 * properly decorated candidates with details.
 *
 * @param groupResultsEtwitterecutor Group results etwitterecutor
 * @tparam Candidate Type of candidates
 * @tparam State The pipeline state domain model.
 */
case class GroupResultsStep[
  Candidate <: UniversalNoun[Any],
  State <: HasCandidatesWithDetails[State] with HasCandidatesWithFeatures[
    Candidate,
    State
  ]] @Inject() (
  groupResultsEtwitterecutor: GroupResultsEtwitterecutor)
    etwittertends Step[State, CandidatePipelineContetwittert, GroupResultsEtwitterecutorInput[
      Candidate
    ], GroupResultsEtwitterecutorResult] {

  override def isEmpty(config: CandidatePipelineContetwittert): Boolean = false
  override def adaptInput(
    state: State,
    config: CandidatePipelineContetwittert
  ): GroupResultsEtwitterecutorInput[Candidate] = {
    val presentationMap = state.candidatesWithDetails.flatMap { candidateWithDetails =>
      candidateWithDetails.presentation
        .map { presentation =>
          candidateWithDetails.getCandidate[UniversalNoun[Any]] -> presentation
        }
    }.toMap
    GroupResultsEtwitterecutorInput(state.candidatesWithFeatures, presentationMap)
  }

  override def arrow(
    config: CandidatePipelineContetwittert,
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[GroupResultsEtwitterecutorInput[Candidate], GroupResultsEtwitterecutorResult] =
    groupResultsEtwitterecutor.arrow(
      config.candidatePipelineIdentifier,
      config.candidateSourceIdentifier,
      contetwittert)

  override def updateState(
    state: State,
    etwitterecutorResult: GroupResultsEtwitterecutorResult,
    config: CandidatePipelineContetwittert
  ): State = state.updateCandidatesWithDetails(etwitterecutorResult.candidatesWithDetails)
}

case class CandidatePipelineContetwittert(
  candidatePipelineIdentifier: CandidatePipelineIdentifier,
  candidateSourceIdentifier: CandidateSourceIdentifier)
