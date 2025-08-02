package com.twitter.product_mitwitterer.core.pipeline.candidate

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.transformer.BaseCandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.gate.ParamGate
import com.twitter.product_mitwitterer.core.gate.ParamGate.EnabledGateSuffitwitter
import com.twitter.product_mitwitterer.core.gate.ParamGate.SupportedClientGateSuffitwitter
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.Component
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.InvalidStepStateEtwitterception
import com.twitter.product_mitwitterer.core.pipeline.PipelineBuilder
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ClosedGate
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureClassifier
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutorResults
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor.CandidateSourceEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor.CandidateSourceEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor.FetchedCandidateWithFeatures
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.StoppedGateEtwitterception
import com.twitter.product_mitwitterer.core.service.group_results_etwitterecutor.GroupResultsEtwitterecutor
import com.twitter.product_mitwitterer.core.service.group_results_etwitterecutor.GroupResultsEtwitterecutorInput
import com.twitter.product_mitwitterer.core.service.group_results_etwitterecutor.GroupResultsEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import com.twitter.stitch.Arrow
import com.twitter.util.logging.Logging
import javatwitter.inject.Inject

class CandidatePipelineBuilder[
  Query <: PipelineQuery,
  CandidateSourceQuery,
  CandidateSourceResult,
  Result <: UniversalNoun[Any]] @Inject() (
  queryFeatureHydratorEtwitterecutor: QueryFeatureHydratorEtwitterecutor,
  asyncFeatureMapEtwitterecutor: AsyncFeatureMapEtwitterecutor,
  candidateDecoratorEtwitterecutor: CandidateDecoratorEtwitterecutor,
  candidateFeatureHydratorEtwitterecutor: CandidateFeatureHydratorEtwitterecutor,
  candidateSourceEtwitterecutor: CandidateSourceEtwitterecutor,
  groupResultsEtwitterecutor: GroupResultsEtwitterecutor,
  filterEtwitterecutor: FilterEtwitterecutor,
  gateEtwitterecutor: GateEtwitterecutor,
  override val statsReceiver: StatsReceiver)
    etwittertends PipelineBuilder[CandidatePipeline.Inputs[Query]]
    with Logging {

  override type UnderlyingResultType = Seq[CandidateWithDetails]
  override type PipelineResultType = IntermediateCandidatePipelineResult[Result]

  def build(
    parentComponentIdentifierStack: ComponentIdentifierStack,
    config: BaseCandidatePipelineConfig[
      Query,
      CandidateSourceQuery,
      CandidateSourceResult,
      Result
    ]
  ): CandidatePipeline[Query] = {

    val pipelineIdentifier = config.identifier
    val candidateSourceIdentifier = config.candidateSource.identifier

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

    // Dynamically replace the identifier of both transformers if config used the inline constructor
    // which sets a default identifier. We need to do this to ensure uniqueness of identifiers.
    val queryTransformer = BaseCandidatePipelineQueryTransformer.copyWithUpdatedIdentifier(
      config.queryTransformer,
      pipelineIdentifier)

    val resultsTransformer = CandidatePipelineResultsTransformer.copyWithUpdatedIdentifier(
      config.resultTransformer,
      pipelineIdentifier)

    val decorator = config.decorator.map(decorator =>
      CandidateDecorator.copyWithUpdatedIdentifier(decorator, pipelineIdentifier))

    val GatesStep = new Step[Query, GateEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = CandidatePipelineConfig.gatesStep

      override def etwitterecutorArrow: Arrow[Query, GateEtwitterecutorResult] = {
        gateEtwitterecutor.arrow(allGates, contetwittert)
      }

      override def inputAdaptor(
        query: CandidatePipeline.Inputs[Query],
        previousResult: IntermediateCandidatePipelineResult[Result]
      ): Query =
        query.query

      override def resultUpdater(
        previousPipelineResult: IntermediateCandidatePipelineResult[Result],
        etwitterecutorResult: GateEtwitterecutorResult
      ): IntermediateCandidatePipelineResult[Result] =
        previousPipelineResult.copy(underlyingResult =
          previousPipelineResult.underlyingResult.copy(gateResult = Some(etwitterecutorResult)))
    }

    def queryFeatureHydrationStep(
      queryFeatureHydrators: Seq[BaseQueryFeatureHydrator[Query, _]],
      stepIdentifier: PipelineStepIdentifier,
      updater: ResultUpdater[CandidatePipelineResult, QueryFeatureHydratorEtwitterecutor.Result]
    ): Step[Query, QueryFeatureHydratorEtwitterecutor.Result] =
      new Step[Query, QueryFeatureHydratorEtwitterecutor.Result] {
        override def identifier: PipelineStepIdentifier = stepIdentifier

        override def etwitterecutorArrow: Arrow[Query, QueryFeatureHydratorEtwitterecutor.Result] =
          queryFeatureHydratorEtwitterecutor.arrow(
            queryFeatureHydrators,
            CandidatePipelineConfig.stepsAsyncFeatureHydrationCanBeCompletedBy,
            contetwittert)

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): Query = query.query

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: QueryFeatureHydratorEtwitterecutor.Result
        ): IntermediateCandidatePipelineResult[Result] =
          previousPipelineResult.copy(
            underlyingResult = updater(previousPipelineResult.underlyingResult, etwitterecutorResult))

        override def queryUpdater(
          query: CandidatePipeline.Inputs[Query],
          etwitterecutorResult: QueryFeatureHydratorEtwitterecutor.Result
        ): CandidatePipeline.Inputs[Query] =
          CandidatePipeline.Inputs(
            query.query
              .withFeatureMap(
                query.query.features.getOrElse(
                  FeatureMap.empty) ++ etwitterecutorResult.featureMap).asInstanceOf[Query],
            query.etwitteristingCandidates)
      }

    def asyncFeaturesStep(
      stepToHydrateFor: PipelineStepIdentifier,
      contetwittert: Etwitterecutor.Contetwittert
    ): Step[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] =
      new Step[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] {
        override def identifier: PipelineStepIdentifier =
          CandidatePipelineConfig.asyncFeaturesStep(stepToHydrateFor)

        override def etwitterecutorArrow: Arrow[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] =
          asyncFeatureMapEtwitterecutor.arrow(stepToHydrateFor, identifier, contetwittert)

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): AsyncFeatureMap =
          previousResult.underlyingResult.mergedAsyncQueryFeatures
            .getOrElse(
              throw InvalidStepStateEtwitterception(identifier, "MergedAsyncQueryFeatures")
            )

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: AsyncFeatureMapEtwitterecutorResults
        ): IntermediateCandidatePipelineResult[Result] =
          previousPipelineResult.copy(
            underlyingResult =
              previousPipelineResult.underlyingResult.copy(asyncFeatureHydrationResults =
                previousPipelineResult.underlyingResult.asyncFeatureHydrationResults match {
                  case Some(etwitteristingResults) => Some(etwitteristingResults ++ etwitterecutorResult)
                  case None => Some(etwitterecutorResult)
                }))

        override def queryUpdater(
          query: CandidatePipeline.Inputs[Query],
          etwitterecutorResult: AsyncFeatureMapEtwitterecutorResults
        ): CandidatePipeline.Inputs[Query] =
          if (etwitterecutorResult.featureMapsByStep
              .getOrElse(stepToHydrateFor, FeatureMap.empty).isEmpty) {
            query
          } else {
            val updatedQuery = query.query
              .withFeatureMap(
                query.query.features
                  .getOrElse(FeatureMap.empty) ++ etwitterecutorResult.featureMapsByStep(
                  stepToHydrateFor)).asInstanceOf[Query]
            CandidatePipeline.Inputs(updatedQuery, query.etwitteristingCandidates)
          }
      }

    val CandidateSourceStep =
      new Step[Query, CandidateSourceEtwitterecutorResult[Result]] {
        override def identifier: PipelineStepIdentifier =
          CandidatePipelineConfig.candidateSourceStep

        override def etwitterecutorArrow: Arrow[
          Query,
          CandidateSourceEtwitterecutorResult[Result]
        ] =
          candidateSourceEtwitterecutor
            .arrow(
              config.candidateSource,
              queryTransformer,
              resultsTransformer,
              config.featuresFromCandidateSourceTransformers,
              contetwittert
            )

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): Query =
          query.query

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: CandidateSourceEtwitterecutorResult[Result]
        ): IntermediateCandidatePipelineResult[Result] =
          previousPipelineResult.copy(underlyingResult =
            previousPipelineResult.underlyingResult.copy(
              candidateSourceResult =
                Some(etwitterecutorResult.asInstanceOf[CandidateSourceEtwitterecutorResult[UniversalNoun[Any]]])
            ))

        override def queryUpdater(
          query: CandidatePipeline.Inputs[Query],
          etwitterecutorResult: CandidateSourceEtwitterecutorResult[Result]
        ): CandidatePipeline.Inputs[Query] = {
          val updatedFeatureMap =
            query.query.features
              .getOrElse(FeatureMap.empty) ++ etwitterecutorResult.candidateSourceFeatureMap
          val updatedQuery = query.query
            .withFeatureMap(updatedFeatureMap).asInstanceOf[Query]
          CandidatePipeline.Inputs(updatedQuery, query.etwitteristingCandidates)
        }
      }

    val PreFilterFeatureHydrationPhase1Step =
      new Step[
        CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
        CandidateFeatureHydratorEtwitterecutorResult[Result]
      ] {
        override def identifier: PipelineStepIdentifier =
          CandidatePipelineConfig.preFilterFeatureHydrationPhase1Step

        override def etwitterecutorArrow: Arrow[
          CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
          CandidateFeatureHydratorEtwitterecutorResult[Result]
        ] =
          candidateFeatureHydratorEtwitterecutor.arrow(config.preFilterFeatureHydrationPhase1, contetwittert)

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result] = {
          val candidateSourceEtwitterecutorResult =
            previousResult.underlyingResult.candidateSourceResult.getOrElse {
              throw InvalidStepStateEtwitterception(identifier, "CandidateSourceResult")
            }
          CandidateFeatureHydratorEtwitterecutor.Inputs(
            query.query,
            candidateSourceEtwitterecutorResult.candidates
              .asInstanceOf[Seq[CandidateWithFeatures[Result]]])
        }

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Result]
        ): IntermediateCandidatePipelineResult[Result] = {
          val candidateSourceEtwitterecutorResult =
            previousPipelineResult.underlyingResult.candidateSourceResult.getOrElse {
              throw InvalidStepStateEtwitterception(identifier, "CandidateSourceResult")
            }

          val featureMapsFromPreFilter = etwitterecutorResult.results.map { result =>
            result.candidate -> result.features
          }.toMap

          val mergedFeatureMaps = candidateSourceEtwitterecutorResult.candidates.map { candidate =>
            val candidateFeatureMap = candidate.features
            val preFilterFeatureMap =
              featureMapsFromPreFilter.getOrElse(
                candidate.candidate.asInstanceOf[Result],
                FeatureMap.empty)

            candidate.candidate.asInstanceOf[Result] -> (candidateFeatureMap ++ preFilterFeatureMap)
          }.toMap

          previousPipelineResult.copy(
            underlyingResult = previousPipelineResult.underlyingResult.copy(
              preFilterHydrationResult = Some(
                etwitterecutorResult
                  .asInstanceOf[CandidateFeatureHydratorEtwitterecutorResult[UniversalNoun[Any]]])
            ),
            featureMaps = Some(mergedFeatureMaps)
          )
        }
      }

    val PreFilterFeatureHydrationPhase2Step =
      new Step[
        CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
        CandidateFeatureHydratorEtwitterecutorResult[Result]
      ] {
        override def identifier: PipelineStepIdentifier =
          CandidatePipelineConfig.preFilterFeatureHydrationPhase2Step

        override def etwitterecutorArrow: Arrow[
          CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
          CandidateFeatureHydratorEtwitterecutorResult[Result]
        ] =
          candidateFeatureHydratorEtwitterecutor.arrow(config.preFilterFeatureHydrationPhase2, contetwittert)

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result] = {
          val candidates = previousResult.underlyingResult.preFilterHydrationResult.getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "PreFilterHydrationResult")
          }.results
          CandidateFeatureHydratorEtwitterecutor.Inputs(
            query.query,
            candidates.asInstanceOf[Seq[CandidateWithFeatures[Result]]]
          )
        }

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Result]
        ): IntermediateCandidatePipelineResult[Result] = {

          val featureMapsFromPreFilterPhase2 = etwitterecutorResult.results.map { result =>
            result.candidate -> result.features
          }.toMap

          val mergedFeatureMaps = previousPipelineResult.featureMaps
            .getOrElse(throw InvalidStepStateEtwitterception(identifier, "FeatureMaps"))
            .map {
              case (candidate, featureMap) =>
                val preFilterPhase2FeatureMap =
                  featureMapsFromPreFilterPhase2.getOrElse(candidate, FeatureMap.empty)

                candidate -> (featureMap ++ preFilterPhase2FeatureMap)
            }

          previousPipelineResult.copy(
            underlyingResult = previousPipelineResult.underlyingResult.copy(
              preFilterHydrationResultPhase2 = Some(
                etwitterecutorResult
                  .asInstanceOf[CandidateFeatureHydratorEtwitterecutorResult[UniversalNoun[Any]]])
            ),
            featureMaps = Some(mergedFeatureMaps)
          )
        }
      }

    val FiltersStep =
      new Step[(Query, Seq[CandidateWithFeatures[Result]]), FilterEtwitterecutorResult[Result]] {
        override def identifier: PipelineStepIdentifier = CandidatePipelineConfig.filtersStep

        override def etwitterecutorArrow: Arrow[
          (Query, Seq[CandidateWithFeatures[Result]]),
          FilterEtwitterecutorResult[
            Result
          ]
        ] =
          filterEtwitterecutor.arrow(config.filters, contetwittert)

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): (Query, Seq[CandidateWithFeatures[Result]]) = {
          val candidates =
            previousResult.underlyingResult.candidateSourceResult
              .getOrElse {
                throw InvalidStepStateEtwitterception(identifier, "CandidateSourceResult")
              }.candidates.map(_.candidate).asInstanceOf[Seq[Result]]

          val featureMaps = previousResult.featureMaps
            .getOrElse(throw InvalidStepStateEtwitterception(identifier, "FeatureMaps"))

          (
            query.query,
            candidates.map(candidate =>
              CandidateWithFeaturesImpl(
                candidate,
                featureMaps.getOrElse(candidate, FeatureMap.empty))))
        }

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: FilterEtwitterecutorResult[Result]
        ): IntermediateCandidatePipelineResult[Result] =
          previousPipelineResult.copy(underlyingResult =
            previousPipelineResult.underlyingResult.copy(
              filterResult =
                Some(etwitterecutorResult.asInstanceOf[FilterEtwitterecutorResult[UniversalNoun[Any]]])
            ))
      }

    val PostFilterFeatureHydrationStep =
      new Step[
        CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
        CandidateFeatureHydratorEtwitterecutorResult[Result]
      ] {
        override def identifier: PipelineStepIdentifier =
          CandidatePipelineConfig.postFilterFeatureHydrationStep

        override def etwitterecutorArrow: Arrow[
          CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
          CandidateFeatureHydratorEtwitterecutorResult[Result]
        ] =
          candidateFeatureHydratorEtwitterecutor.arrow(config.postFilterFeatureHydration, contetwittert)

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result] = {
          val filterResult = previousResult.underlyingResult.filterResult
            .getOrElse(
              throw InvalidStepStateEtwitterception(identifier, "FilterResult")
            ).result.asInstanceOf[Seq[Result]]

          val featureMaps = previousResult.featureMaps.getOrElse(
            throw InvalidStepStateEtwitterception(identifier, "FeatureMaps")
          )

          val filteredCandidates = filterResult.map { candidate =>
            CandidateWithFeaturesImpl(candidate, featureMaps.getOrElse(candidate, FeatureMap.empty))
          }
          CandidateFeatureHydratorEtwitterecutor.Inputs(query.query, filteredCandidates)
        }

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Result]
        ): IntermediateCandidatePipelineResult[Result] = {
          val filterResult = previousPipelineResult.underlyingResult.filterResult
            .getOrElse(
              throw InvalidStepStateEtwitterception(identifier, "FilterResult")
            ).result.asInstanceOf[Seq[Result]]

          val featureMaps = previousPipelineResult.featureMaps.getOrElse(
            throw InvalidStepStateEtwitterception(identifier, "FeatureMaps")
          )

          val postFilterFeatureMaps = etwitterecutorResult.results.map { result =>
            result.candidate -> result.features
          }.toMap

          val mergedFeatureMaps = filterResult.map { candidate =>
            candidate ->
              (featureMaps
                .getOrElse(candidate, FeatureMap.empty) ++ postFilterFeatureMaps.getOrElse(
                candidate,
                FeatureMap.empty))
          }.toMap

          previousPipelineResult.copy(
            underlyingResult = previousPipelineResult.underlyingResult.copy(
              postFilterHydrationResult = Some(
                etwitterecutorResult
                  .asInstanceOf[CandidateFeatureHydratorEtwitterecutorResult[UniversalNoun[Any]]])
            ),
            featureMaps = Some(mergedFeatureMaps)
          )
        }
      }

    val ScorersStep =
      new Step[
        CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
        CandidateFeatureHydratorEtwitterecutorResult[Result]
      ] {
        override def identifier: PipelineStepIdentifier = CandidatePipelineConfig.scorersStep

        override def etwitterecutorArrow: Arrow[
          CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
          CandidateFeatureHydratorEtwitterecutorResult[Result]
        ] =
          candidateFeatureHydratorEtwitterecutor.arrow(config.scorers, contetwittert)

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result] = {
          val filterResult = previousResult.underlyingResult.filterResult
            .getOrElse(
              throw InvalidStepStateEtwitterception(identifier, "FilterResult")
            ).result.asInstanceOf[Seq[Result]]

          val featureMaps = previousResult.featureMaps.getOrElse(
            throw InvalidStepStateEtwitterception(identifier, "FeatureMaps")
          )

          val filteredCandidates = filterResult.map { candidate =>
            CandidateWithFeaturesImpl(candidate, featureMaps.getOrElse(candidate, FeatureMap.empty))
          }
          CandidateFeatureHydratorEtwitterecutor.Inputs(query.query, filteredCandidates)
        }

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Result]
        ): IntermediateCandidatePipelineResult[Result] = {
          val filterResult = previousPipelineResult.underlyingResult.filterResult
            .getOrElse(
              throw InvalidStepStateEtwitterception(identifier, "FilterResult")
            ).result.asInstanceOf[Seq[Result]]

          val featureMaps = previousPipelineResult.featureMaps.getOrElse(
            throw InvalidStepStateEtwitterception(identifier, "FeatureMaps")
          )

          val scoringFeatureMaps = etwitterecutorResult.results.map { result =>
            result.candidate -> result.features
          }.toMap

          val mergedFeatureMaps = filterResult.map { candidate =>
            candidate ->
              (featureMaps
                .getOrElse(candidate, FeatureMap.empty) ++ scoringFeatureMaps.getOrElse(
                candidate,
                FeatureMap.empty))
          }.toMap

          previousPipelineResult.copy(
            underlyingResult = previousPipelineResult.underlyingResult.copy(
              scorersResult = Some(
                etwitterecutorResult
                  .asInstanceOf[CandidateFeatureHydratorEtwitterecutorResult[UniversalNoun[Any]]])
            ),
            featureMaps = Some(mergedFeatureMaps)
          )
        }
      }

    val DecorationStep =
      new Step[(Query, Seq[CandidateWithFeatures[Result]]), CandidateDecoratorEtwitterecutorResult] {
        override def identifier: PipelineStepIdentifier = CandidatePipelineConfig.decoratorStep

        override def etwitterecutorArrow: Arrow[
          (Query, Seq[CandidateWithFeatures[Result]]),
          CandidateDecoratorEtwitterecutorResult
        ] =
          candidateDecoratorEtwitterecutor.arrow(decorator, contetwittert)

        override def inputAdaptor(
          query: CandidatePipeline.Inputs[Query],
          previousResult: IntermediateCandidatePipelineResult[Result]
        ): (Query, Seq[CandidateWithFeatures[Result]]) = {
          val keptCandidates = previousResult.underlyingResult.filterResult
            .getOrElse {
              throw InvalidStepStateEtwitterception(identifier, "FilterResult")
            }.result.asInstanceOf[Seq[Result]]

          val featureMaps = previousResult.featureMaps.getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "FeatureMaps")
          }

          (
            query.query,
            keptCandidates.map(candidate =>
              CandidateWithFeaturesImpl(
                candidate,
                featureMaps.getOrElse(candidate, FeatureMap.empty))))
        }

        override def resultUpdater(
          previousPipelineResult: IntermediateCandidatePipelineResult[Result],
          etwitterecutorResult: CandidateDecoratorEtwitterecutorResult
        ): IntermediateCandidatePipelineResult[Result] =
          previousPipelineResult.copy(underlyingResult =
            previousPipelineResult.underlyingResult.copy(
              candidateDecoratorResult = Some(etwitterecutorResult)
            ))
      }

    /**
     * ResultStep is a synchronous step that basically takes the outputs from the other steps, groups modules,
     * and puts things into the final result object
     */
    val ResultStep = new Step[GroupResultsEtwitterecutorInput[Result], GroupResultsEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = CandidatePipelineConfig.resultStep

      override def etwitterecutorArrow: Arrow[
        GroupResultsEtwitterecutorInput[Result],
        GroupResultsEtwitterecutorResult
      ] = groupResultsEtwitterecutor.arrow(pipelineIdentifier, candidateSourceIdentifier, contetwittert)

      override def inputAdaptor(
        query: CandidatePipeline.Inputs[Query],
        previousResult: IntermediateCandidatePipelineResult[Result]
      ): GroupResultsEtwitterecutorInput[Result] = {

        val underlying = previousResult.underlyingResult

        val keptCandidates = underlying.filterResult
          .getOrElse(
            throw InvalidStepStateEtwitterception(identifier, "FilterResult")
          ).result.asInstanceOf[Seq[Result]]

        val decorations = underlying.candidateDecoratorResult
          .getOrElse(
            throw InvalidStepStateEtwitterception(identifier, "DecorationResult")
          ).result.map(decoration => decoration.candidate -> decoration.presentation).toMap

        val combinedFeatureMaps: Map[Result, FeatureMap] = previousResult.featureMaps.getOrElse(
          throw InvalidStepStateEtwitterception(identifier, "FeatureMaps"))

        val filteredCandidates = keptCandidates.map { candidate =>
          val updatedMap = combinedFeatureMaps
            .get(candidate).getOrElse(FeatureMap.empty)
          FetchedCandidateWithFeatures(candidate, updatedMap)
        }

        GroupResultsEtwitterecutorInput(
          candidates = filteredCandidates,
          decorations = decorations
        )
      }

      override def resultUpdater(
        previousPipelineResult: IntermediateCandidatePipelineResult[Result],
        etwitterecutorResult: GroupResultsEtwitterecutorResult
      ): IntermediateCandidatePipelineResult[Result] =
        previousPipelineResult.copy(underlyingResult = previousPipelineResult.underlyingResult
          .copy(result = Some(etwitterecutorResult.candidatesWithDetails)))
    }

    val builtSteps = Seq(
      GatesStep,
      queryFeatureHydrationStep(
        config.queryFeatureHydration,
        CandidatePipelineConfig.fetchQueryFeaturesStep,
        (pipelineResult, etwitterecutorResult) =>
          pipelineResult.copy(queryFeatures = Some(etwitterecutorResult))
      ),
      queryFeatureHydrationStep(
        config.queryFeatureHydrationPhase2,
        CandidatePipelineConfig.fetchQueryFeaturesPhase2Step,
        (pipelineResult, etwitterecutorResult) =>
          pipelineResult.copy(
            queryFeaturesPhase2 = Some(etwitterecutorResult),
            mergedAsyncQueryFeatures = Some(
              pipelineResult.queryFeatures
                .getOrElse(
                  throw InvalidStepStateEtwitterception(
                    CandidatePipelineConfig.fetchQueryFeaturesPhase2Step,
                    "QueryFeatures")
                ).asyncFeatureMap ++ etwitterecutorResult.asyncFeatureMap)
          )
      ),
      asyncFeaturesStep(CandidatePipelineConfig.candidateSourceStep, contetwittert),
      CandidateSourceStep,
      asyncFeaturesStep(CandidatePipelineConfig.preFilterFeatureHydrationPhase1Step, contetwittert),
      PreFilterFeatureHydrationPhase1Step,
      asyncFeaturesStep(CandidatePipelineConfig.preFilterFeatureHydrationPhase2Step, contetwittert),
      PreFilterFeatureHydrationPhase2Step,
      asyncFeaturesStep(CandidatePipelineConfig.filtersStep, contetwittert),
      FiltersStep,
      asyncFeaturesStep(CandidatePipelineConfig.postFilterFeatureHydrationStep, contetwittert),
      PostFilterFeatureHydrationStep,
      asyncFeaturesStep(CandidatePipelineConfig.scorersStep, contetwittert),
      ScorersStep,
      asyncFeaturesStep(CandidatePipelineConfig.decoratorStep, contetwittert),
      DecorationStep,
      ResultStep
    )

    /** The main etwitterecution logic for this Candidate Pipeline. */
    val finalArrow: Arrow[CandidatePipeline.Inputs[Query], CandidatePipelineResult] =
      buildCombinedArrowFromSteps(
        steps = builtSteps,
        contetwittert = contetwittert,
        initialEmptyResult =
          IntermediateCandidatePipelineResult.empty[Result](config.candidateSource.identifier),
        stepsInOrderFromConfig = CandidatePipelineConfig.stepsInOrder
      ).map(_.underlyingResult)

    val configFromBuilder = config
    new CandidatePipeline[Query] {
      override private[core] val config: BaseCandidatePipelineConfig[Query, _, _, _] =
        configFromBuilder
      override val arrow: Arrow[CandidatePipeline.Inputs[Query], CandidatePipelineResult] =
        finalArrow
      override val identifier: CandidatePipelineIdentifier = pipelineIdentifier
      override val alerts: Seq[Alert] = config.alerts
      override val children: Seq[Component] =
        allGates ++
          config.queryFeatureHydration ++
          Seq(queryTransformer, config.candidateSource, resultsTransformer) ++
          config.featuresFromCandidateSourceTransformers ++
          decorator.toSeq ++
          config.preFilterFeatureHydrationPhase1 ++
          config.filters ++
          config.postFilterFeatureHydration ++
          config.scorers
    }
  }

  private case class CandidateWithFeaturesImpl(candidate: Result, features: FeatureMap)
      etwittertends CandidateWithFeatures[Result]
}
