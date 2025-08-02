package com.twitter.product_mitwitterer.core.pipeline.scoring

import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.decorator.Decoration
import com.twitter.product_mitwitterer.core.functional_component.scorer.ScoredCandidateResult
import com.twitter.product_mitwitterer.core.gate.ParamGate
import com.twitter.product_mitwitterer.core.gate.ParamGate._
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.Component
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.NewPipelineBuilder
import com.twitter.product_mitwitterer.core.pipeline.NewPipelineArrowBuilder
import com.twitter.product_mitwitterer.core.pipeline.NewPipelineResult
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ClosedGate
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureClassifier
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithDetails
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithFeatures
import com.twitter.product_mitwitterer.core.pipeline.state.HasEtwitterecutorResults
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasResult
import com.twitter.product_mitwitterer.core.pipeline.step.candidate_feature_hydrator.CandidateFeatureHydratorStep
import com.twitter.product_mitwitterer.core.pipeline.step.gate.GateStep
import com.twitter.product_mitwitterer.core.pipeline.step.scorer.ScorerStep
import com.twitter.product_mitwitterer.core.pipeline.step.selector.SelectorStep
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.StoppedGateEtwitterception
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject
import scala.collection.immutable.ListMap

/**
 * NewScoringPipelineBuilder builds [[ScoringPipeline]]s from [[ScoringPipelineConfig]]s.
 * New because it's meant to eventually replace [[ScoringPipelineBuilder]]
 * You should inject a [[ScoringPipelineBuilderFactory]] and call `.get` to build these.
 *
 * @see [[ScoringPipelineConfig]] for the description of the type parameters
 * @tparam Query the type of query these accept.
 * @tparam Candidate the domain model for the candidate being scored
 */
class NewScoringPipelineBuilder[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]] @Inject() (
  selectionStep: SelectorStep[Query, ScoringPipelineState[Query, Candidate]],
  gateStep: GateStep[Query, ScoringPipelineState[Query, Candidate]],
  candidateFeatureHydrationStep: CandidateFeatureHydratorStep[
    Query,
    Candidate,
    ScoringPipelineState[Query, Candidate]
  ],
  scorerStep: ScorerStep[Query, Candidate, ScoringPipelineState[Query, Candidate]])
    etwittertends NewPipelineBuilder[ScoringPipelineConfig[Query, Candidate], Seq[
      CandidateWithFeatures[Candidate]
    ], ScoringPipelineState[Query, Candidate], ScoringPipeline[Query, Candidate]] {

  override def build(
    parentComponentIdentifierStack: ComponentIdentifierStack,
    arrowBuilder: NewPipelineArrowBuilder[ArrowResult, ArrowState],
    scoringPipelineConfig: ScoringPipelineConfig[Query, Candidate]
  ): ScoringPipeline[Query, Candidate] = {
    val pipelineIdentifier = scoringPipelineConfig.identifier

    val contetwittert = Etwitterecutor.Contetwittert(
      PipelineFailureClassifier(
        scoringPipelineConfig.failureClassifier.orElse(
          StoppedGateEtwitterception.classifier(ClosedGate))),
      parentComponentIdentifierStack.push(pipelineIdentifier)
    )

    val enabledGateOpt = scoringPipelineConfig.enabledDeciderParam.map { deciderParam =>
      ParamGate(pipelineIdentifier + EnabledGateSuffitwitter, deciderParam)
    }
    val supportedClientGateOpt = scoringPipelineConfig.supportedClientParam.map { param =>
      ParamGate(pipelineIdentifier + SupportedClientGateSuffitwitter, param)
    }

    /**
     * Evaluate enabled decider gate first since if it's off, there is no reason to proceed
     * Netwittert evaluate supported client feature switch gate, followed by customer configured gates
     */
    val allGates =
      enabledGateOpt.toSeq ++ supportedClientGateOpt.toSeq ++ scoringPipelineConfig.gates

    val underlyingArrow = arrowBuilder
      .add(ScoringPipelineConfig.gatesStep, gateStep, allGates)
      .add(ScoringPipelineConfig.selectorsStep, selectionStep, scoringPipelineConfig.selectors)
      .add(
        ScoringPipelineConfig.preScoringFeatureHydrationPhase1Step,
        candidateFeatureHydrationStep,
        scoringPipelineConfig.preScoringFeatureHydrationPhase1)
      .add(
        ScoringPipelineConfig.preScoringFeatureHydrationPhase2Step,
        candidateFeatureHydrationStep,
        scoringPipelineConfig.preScoringFeatureHydrationPhase2)
      .add(ScoringPipelineConfig.scorersStep, scorerStep, scoringPipelineConfig.scorers).buildArrow(
        contetwittert)

    val finalArrow = Arrow
      .map { inputs: ScoringPipeline.Inputs[Query] =>
        ScoringPipelineState[Query, Candidate](inputs.query, inputs.candidates, ListMap.empty)
      }.andThen(underlyingArrow).map { pipelineResult =>
        ScoringPipelineResult(
          gateResults = pipelineResult.etwitterecutorResultsByPipelineStep
            .get(ScoringPipelineConfig.gatesStep)
            .map(_.asInstanceOf[GateEtwitterecutorResult]),
          selectorResults = pipelineResult.etwitterecutorResultsByPipelineStep
            .get(ScoringPipelineConfig.selectorsStep)
            .map(_.asInstanceOf[SelectorEtwitterecutorResult]),
          preScoringHydrationPhase1Result = pipelineResult.etwitterecutorResultsByPipelineStep
            .get(ScoringPipelineConfig.preScoringFeatureHydrationPhase1Step)
            .map(_.asInstanceOf[CandidateFeatureHydratorEtwitterecutorResult[Candidate]]),
          preScoringHydrationPhase2Result = pipelineResult.etwitterecutorResultsByPipelineStep
            .get(ScoringPipelineConfig.preScoringFeatureHydrationPhase2Step)
            .map(_.asInstanceOf[CandidateFeatureHydratorEtwitterecutorResult[Candidate]]),
          scorerResults = pipelineResult.etwitterecutorResultsByPipelineStep
            .get(ScoringPipelineConfig.scorersStep)
            .map(_.asInstanceOf[CandidateFeatureHydratorEtwitterecutorResult[Candidate]]),
          failure = pipelineResult match {
            case failure: NewPipelineResult.Failure =>
              Some(failure.failure)
            case _ => None
          },
          result = pipelineResult match {
            case result: NewPipelineResult.Success[Seq[CandidateWithFeatures[Candidate]]] =>
              Some(result.result.map { candidateWithFeatures =>
                ScoredCandidateResult(
                  candidateWithFeatures.candidate,
                  candidateWithFeatures.features)
              })
            case _ => None
          }
        )
      }

    new ScoringPipeline[Query, Candidate] {
      override val arrow: Arrow[ScoringPipeline.Inputs[Query], ScoringPipelineResult[Candidate]] =
        finalArrow

      override val identifier: ScoringPipelineIdentifier = scoringPipelineConfig.identifier

      override val alerts: Seq[Alert] = scoringPipelineConfig.alerts

      override val children: Seq[Component] =
        allGates ++ scoringPipelineConfig.preScoringFeatureHydrationPhase1 ++ scoringPipelineConfig.preScoringFeatureHydrationPhase2 ++ scoringPipelineConfig.scorers

      override private[core] val config = scoringPipelineConfig
    }
  }
}

case class ScoringPipelineState[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
  override val query: Query,
  candidates: Seq[ItemCandidateWithDetails],
  override val etwitterecutorResultsByPipelineStep: ListMap[PipelineStepIdentifier, EtwitterecutorResult])
    etwittertends HasQuery[Query, ScoringPipelineState[Query, Candidate]]
    with HasCandidatesWithDetails[ScoringPipelineState[Query, Candidate]]
    with HasCandidatesWithFeatures[Candidate, ScoringPipelineState[Query, Candidate]]
    with HasEtwitterecutorResults[ScoringPipelineState[Query, Candidate]]
    with HasResult[Seq[CandidateWithFeatures[Candidate]]] {

  override val candidatesWithDetails: Seq[CandidateWithDetails] = candidates

  override val candidatesWithFeatures: Seq[CandidateWithFeatures[Candidate]] =
    candidates.asInstanceOf[Seq[CandidateWithFeatures[Candidate]]]

  override val buildResult: Seq[CandidateWithFeatures[Candidate]] = candidatesWithFeatures

  override def updateCandidatesWithDetails(
    newCandidates: Seq[CandidateWithDetails]
  ): ScoringPipelineState[Query, Candidate] = {
    this.copy(candidates = newCandidates.asInstanceOf[Seq[ItemCandidateWithDetails]])
  }

  override def updateQuery(newQuery: Query): ScoringPipelineState[Query, Candidate] =
    this.copy(query = newQuery)

  override def updateDecorations(
    decoration: Seq[Decoration]
  ): ScoringPipelineState[Query, Candidate] = ???

  override def updateCandidatesWithFeatures(
    newCandidates: Seq[CandidateWithFeatures[Candidate]]
  ): ScoringPipelineState[Query, Candidate] = {
    val updatedCandidates = candidates.zip(newCandidates).map {
      case (itemCandidateWithDetails, newCandidate) =>
        itemCandidateWithDetails.copy(features =
          itemCandidateWithDetails.features ++ newCandidate.features)
    }
    this.copy(query, updatedCandidates)
  }

  override private[pipeline] def setEtwitterecutorResults(
    newMap: ListMap[PipelineStepIdentifier, EtwitterecutorResult]
  ) = this.copy(etwitterecutorResultsByPipelineStep = newMap)
}
