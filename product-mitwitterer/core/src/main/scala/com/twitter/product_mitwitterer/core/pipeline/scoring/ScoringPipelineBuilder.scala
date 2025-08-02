package com.twitter.product_mitwitterer.core.pipeline.scoring

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.scorer.ScoredCandidateResult
import com.twitter.product_mitwitterer.core.gate.ParamGate
import com.twitter.product_mitwitterer.core.gate.ParamGate.EnabledGateSuffitwitter
import com.twitter.product_mitwitterer.core.gate.ParamGate.SupportedClientGateSuffitwitter
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.Component
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack
import com.twitter.product_mitwitterer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.InvalidStepStateEtwitterception
import com.twitter.product_mitwitterer.core.pipeline.PipelineBuilder
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ClosedGate
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureClassifier
import com.twitter.product_mitwitterer.core.pipeline.scoring.ScoringPipeline.Inputs
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.StoppedGateEtwitterception
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * ScoringPipelineBuilder builds [[ScoringPipeline]]s from [[ScoringPipelineConfig]]s.
 *
 * You should inject a [[ScoringPipelineBuilderFactory]] and call `.get` to build these.
 *
 * @see [[ScoringPipelineConfig]] for the description of the type parameters
 * @tparam Query the type of query these accept.
 * @tparam Candidate the domain model for the candidate being scored
 */
class ScoringPipelineBuilder[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]] @Inject() (
  gateEtwitterecutor: GateEtwitterecutor,
  selectorEtwitterecutor: SelectorEtwitterecutor,
  candidateFeatureHydratorEtwitterecutor: CandidateFeatureHydratorEtwitterecutor,
  override val statsReceiver: StatsReceiver)
    etwittertends PipelineBuilder[Inputs[Query]] {

  override type UnderlyingResultType = Seq[ScoredCandidateResult[Candidate]]
  override type PipelineResultType = ScoringPipelineResult[Candidate]

  def build(
    parentComponentIdentifierStack: ComponentIdentifierStack,
    config: ScoringPipelineConfig[Query, Candidate]
  ): ScoringPipeline[Query, Candidate] = {

    val pipelineIdentifier = config.identifier

    val contetwittert = Etwitterecutor.Contetwittert(
      PipelineFailureClassifier(
        config.failureClassifier.orElse(StoppedGateEtwitterception.classifier(ClosedGate))),
      parentComponentIdentifierStack.push(pipelineIdentifier)
    )

    val enabledGateOpt = config.enabledDeciderParam.map { deciderParam =>
      ParamGate(pipelineIdentifier + EnabledGateSuffitwitter, deciderParam)
    }
    val supportedClientGateOpt = config.supportedClientParam.map { param =>
      ParamGate(pipelineIdentifier + SupportedClientGateSuffitwitter, param)
    }

    /**
     * Evaluate enabled decider gate first since if it's off, there is no reason to proceed
     * Netwittert evaluate supported client feature switch gate, followed by customer configured gates
     */
    val allGates = enabledGateOpt.toSeq ++ supportedClientGateOpt.toSeq ++ config.gates

    val GatesStep = new Step[Query, GateEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = ScoringPipelineConfig.gatesStep

      override lazy val etwitterecutorArrow: Arrow[Query, GateEtwitterecutorResult] =
        gateEtwitterecutor.arrow(allGates, contetwittert)

      override def inputAdaptor(
        query: ScoringPipeline.Inputs[Query],
        previousResult: ScoringPipelineResult[Candidate]
      ): Query = {
        query.query
      }

      override def resultUpdater(
        previousPipelineResult: ScoringPipelineResult[Candidate],
        etwitterecutorResult: GateEtwitterecutorResult
      ): ScoringPipelineResult[Candidate] =
        previousPipelineResult.copy(gateResults = Some(etwitterecutorResult))
    }

    val SelectorsStep = new Step[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = ScoringPipelineConfig.selectorsStep

      override def etwitterecutorArrow: Arrow[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] =
        selectorEtwitterecutor.arrow(config.selectors, contetwittert)

      override def inputAdaptor(
        query: ScoringPipeline.Inputs[Query],
        previousResult: ScoringPipelineResult[Candidate]
      ): SelectorEtwitterecutor.Inputs[Query] = SelectorEtwitterecutor.Inputs(query.query, query.candidates)

      override def resultUpdater(
        previousPipelineResult: ScoringPipelineResult[Candidate],
        etwitterecutorResult: SelectorEtwitterecutorResult
      ): ScoringPipelineResult[Candidate] =
        previousPipelineResult.copy(selectorResults = Some(etwitterecutorResult))
    }

    val PreScoringFeatureHydrationPhase1Step =
      new Step[
        CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
        CandidateFeatureHydratorEtwitterecutorResult[Candidate]
      ] {
        override def identifier: PipelineStepIdentifier =
          ScoringPipelineConfig.preScoringFeatureHydrationPhase1Step

        override def etwitterecutorArrow: Arrow[
          CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
          CandidateFeatureHydratorEtwitterecutorResult[Candidate]
        ] =
          candidateFeatureHydratorEtwitterecutor.arrow(config.preScoringFeatureHydrationPhase1, contetwittert)

        override def inputAdaptor(
          query: ScoringPipeline.Inputs[Query],
          previousResult: ScoringPipelineResult[Candidate]
        ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate] = {
          val selectedCandidatesResult = previousResult.selectorResults.getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "SelectorResults")
          }.selectedCandidates

          CandidateFeatureHydratorEtwitterecutor.Inputs(
            query.query,
            selectedCandidatesResult.asInstanceOf[Seq[CandidateWithFeatures[Candidate]]])
        }

        override def resultUpdater(
          previousPipelineResult: ScoringPipelineResult[Candidate],
          etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Candidate]
        ): ScoringPipelineResult[Candidate] = previousPipelineResult.copy(
          preScoringHydrationPhase1Result = Some(etwitterecutorResult)
        )
      }

    val PreScoringFeatureHydrationPhase2Step =
      new Step[
        CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
        CandidateFeatureHydratorEtwitterecutorResult[Candidate]
      ] {
        override def identifier: PipelineStepIdentifier =
          ScoringPipelineConfig.preScoringFeatureHydrationPhase2Step

        override def etwitterecutorArrow: Arrow[
          CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
          CandidateFeatureHydratorEtwitterecutorResult[Candidate]
        ] =
          candidateFeatureHydratorEtwitterecutor.arrow(config.preScoringFeatureHydrationPhase2, contetwittert)

        override def inputAdaptor(
          query: ScoringPipeline.Inputs[Query],
          previousResult: ScoringPipelineResult[Candidate]
        ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate] = {
          val preScoringHydrationPhase1FeatureMaps: Seq[FeatureMap] =
            previousResult.preScoringHydrationPhase1Result
              .getOrElse(
                throw InvalidStepStateEtwitterception(identifier, "PreScoringHydrationPhase1Result"))
              .results.map(_.features)

          val itemCandidates = previousResult.selectorResults
            .getOrElse(throw InvalidStepStateEtwitterception(identifier, "SelectionResults"))
            .selectedCandidates.collect {
              case itemCandidate: ItemCandidateWithDetails => itemCandidate
            }
          // If there is no feature hydration (empty results), no need to attempt merging.
          val candidates = if (preScoringHydrationPhase1FeatureMaps.isEmpty) {
            itemCandidates
          } else {
            itemCandidates.zip(preScoringHydrationPhase1FeatureMaps).map {
              case (itemCandidate, featureMap) =>
                itemCandidate.copy(features = itemCandidate.features ++ featureMap)
            }
          }

          CandidateFeatureHydratorEtwitterecutor.Inputs(
            query.query,
            candidates.asInstanceOf[Seq[CandidateWithFeatures[Candidate]]])
        }

        override def resultUpdater(
          previousPipelineResult: ScoringPipelineResult[Candidate],
          etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Candidate]
        ): ScoringPipelineResult[Candidate] = previousPipelineResult.copy(
          preScoringHydrationPhase2Result = Some(etwitterecutorResult)
        )
      }

    def getMergedPreScoringFeatureMap(
      stepIdentifier: PipelineStepIdentifier,
      previousResult: ScoringPipelineResult[Candidate]
    ): Seq[FeatureMap] = {
      val preScoringHydrationPhase1FeatureMaps: Seq[FeatureMap] =
        previousResult.preScoringHydrationPhase1Result
          .getOrElse(
            throw InvalidStepStateEtwitterception(
              stepIdentifier,
              "PreScoringHydrationPhase1Result")).results.map(_.features)

      val preScoringHydrationPhase2FeatureMaps: Seq[FeatureMap] =
        previousResult.preScoringHydrationPhase2Result
          .getOrElse(
            throw InvalidStepStateEtwitterception(
              stepIdentifier,
              "PreScoringHydrationPhase2Result")).results.map(_.features)
      /*
       * If either pre-scoring hydration phase feature map is empty, no need to merge them,
       * we can just take all non-empty ones.
       */
      if (preScoringHydrationPhase1FeatureMaps.isEmpty) {
        preScoringHydrationPhase2FeatureMaps
      } else if (preScoringHydrationPhase2FeatureMaps.isEmpty) {
        preScoringHydrationPhase1FeatureMaps
      } else {
        // No need to check the size in both, since the inputs to both hydration phases are the
        // same and each phase ensures the number of candidates and ordering matches the input.
        preScoringHydrationPhase1FeatureMaps.zip(preScoringHydrationPhase2FeatureMaps).map {
          case (preScoringHydrationPhase1FeatureMap, preScoringHydrationPhasesFeatureMap) =>
            preScoringHydrationPhase1FeatureMap ++ preScoringHydrationPhasesFeatureMap
        }
      }
    }

    val ScorersStep =
      new Step[
        CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
        CandidateFeatureHydratorEtwitterecutorResult[Candidate]
      ] {
        override def identifier: PipelineStepIdentifier = ScoringPipelineConfig.scorersStep

        override def inputAdaptor(
          query: ScoringPipeline.Inputs[Query],
          previousResult: ScoringPipelineResult[Candidate]
        ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate] = {

          val mergedPreScoringFeatureHydrationFeatures: Seq[FeatureMap] =
            getMergedPreScoringFeatureMap(ScoringPipelineConfig.scorersStep, previousResult)

          val itemCandidates = previousResult.selectorResults
            .getOrElse(throw InvalidStepStateEtwitterception(identifier, "SelectionResults"))
            .selectedCandidates.collect {
              case itemCandidate: ItemCandidateWithDetails => itemCandidate
            }

          // If there was no pre-scoring features hydration, no need to re-merge feature maps
          // and construct a new item candidate
          val updatedCandidates = if (mergedPreScoringFeatureHydrationFeatures.isEmpty) {
            itemCandidates
          } else {
            itemCandidates.zip(mergedPreScoringFeatureHydrationFeatures).map {
              case (itemCandidate, preScoringFeatureMap) =>
                itemCandidate.copy(features = itemCandidate.features ++ preScoringFeatureMap)
            }
          }
          CandidateFeatureHydratorEtwitterecutor.Inputs(
            query.query,
            updatedCandidates.asInstanceOf[Seq[CandidateWithFeatures[Candidate]]])
        }

        override lazy val etwitterecutorArrow: Arrow[
          CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
          CandidateFeatureHydratorEtwitterecutorResult[
            Candidate
          ]
        ] = candidateFeatureHydratorEtwitterecutor.arrow(config.scorers.toSeq, contetwittert)

        override def resultUpdater(
          previousPipelineResult: ScoringPipelineResult[Candidate],
          etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Candidate]
        ): ScoringPipelineResult[Candidate] =
          previousPipelineResult.copy(scorerResults = Some(etwitterecutorResult))
      }

    val ResultStep =
      new Step[Seq[CandidateWithFeatures[UniversalNoun[Any]]], Seq[
        CandidateWithFeatures[UniversalNoun[Any]]
      ]] {
        override def identifier: PipelineStepIdentifier = ScoringPipelineConfig.resultStep

        override def etwitterecutorArrow: Arrow[Seq[CandidateWithFeatures[UniversalNoun[Any]]], Seq[
          CandidateWithFeatures[UniversalNoun[Any]]
        ]] = Arrow.identity

        override def inputAdaptor(
          query: Inputs[Query],
          previousResult: ScoringPipelineResult[Candidate]
        ): Seq[CandidateWithFeatures[UniversalNoun[Any]]] = previousResult.selectorResults
          .getOrElse(throw InvalidStepStateEtwitterception(identifier, "SelectionResults"))
          .selectedCandidates.collect {
            case itemCandidate: ItemCandidateWithDetails => itemCandidate
          }

        override def resultUpdater(
          previousPipelineResult: ScoringPipelineResult[Candidate],
          etwitterecutorResult: Seq[CandidateWithFeatures[UniversalNoun[Any]]]
        ): ScoringPipelineResult[Candidate] = {
          val scorerResults: Seq[FeatureMap] = previousPipelineResult.scorerResults
            .getOrElse(throw InvalidStepStateEtwitterception(identifier, "ScorerResult")).results.map(
              _.features)

          val mergedPreScoringFeatureHydrationFeatureMaps: Seq[FeatureMap] =
            getMergedPreScoringFeatureMap(ScoringPipelineConfig.resultStep, previousPipelineResult)

          val itemCandidates = etwitterecutorResult.asInstanceOf[Seq[ItemCandidateWithDetails]]
          val finalFeatureMap = if (mergedPreScoringFeatureHydrationFeatureMaps.isEmpty) {
            scorerResults
          } else {
            scorerResults
              .zip(mergedPreScoringFeatureHydrationFeatureMaps).map {
                case (preScoringFeatureMap, scoringFeatureMap) =>
                  preScoringFeatureMap ++ scoringFeatureMap
              }
          }

          val finalResults = itemCandidates.zip(finalFeatureMap).map {
            case (itemCandidate, featureMap) =>
              ScoredCandidateResult(itemCandidate.candidate.asInstanceOf[Candidate], featureMap)
          }
          previousPipelineResult.withResult(finalResults)
        }
      }

    val builtSteps = Seq(
      GatesStep,
      SelectorsStep,
      PreScoringFeatureHydrationPhase1Step,
      PreScoringFeatureHydrationPhase2Step,
      ScorersStep,
      ResultStep
    )

    /** The main etwitterecution logic for this Candidate Pipeline. */
    val finalArrow: Arrow[ScoringPipeline.Inputs[Query], ScoringPipelineResult[Candidate]] =
      buildCombinedArrowFromSteps(
        steps = builtSteps,
        contetwittert = contetwittert,
        initialEmptyResult = ScoringPipelineResult.empty,
        stepsInOrderFromConfig = ScoringPipelineConfig.stepsInOrder
      )

    val configFromBuilder = config
    new ScoringPipeline[Query, Candidate] {
      override private[core] val config: ScoringPipelineConfig[Query, Candidate] = configFromBuilder
      override val arrow: Arrow[ScoringPipeline.Inputs[Query], ScoringPipelineResult[Candidate]] =
        finalArrow
      override val identifier: ScoringPipelineIdentifier = pipelineIdentifier
      override val alerts: Seq[Alert] = config.alerts
      override val children: Seq[Component] =
        allGates ++ config.preScoringFeatureHydrationPhase1 ++ config.preScoringFeatureHydrationPhase2 ++ config.scorers
    }
  }
}
