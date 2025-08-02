package com.twitter.product_mitwitterer.core.pipeline.recommendation

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.util.logging.Logging
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.Component
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack
import com.twitter.product_mitwitterer.core.model.common.identifier.RecommendationPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemPresentation
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.FailOpenPolicy
import com.twitter.product_mitwitterer.core.pipeline.InvalidStepStateEtwitterception
import com.twitter.product_mitwitterer.core.pipeline.PipelineBuilder
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipeline
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineBuilderFactory
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.MisconfiguredDecorator
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureClassifier
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ProductDisabled
import com.twitter.product_mitwitterer.core.pipeline.scoring.ScoringPipeline
import com.twitter.product_mitwitterer.core.pipeline.scoring.ScoringPipelineBuilderFactory
import com.twitter.product_mitwitterer.core.pipeline.scoring.ScoringPipelineConfig
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorObserver
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorStatus
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutorResults
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor.CandidateDecoratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor.CandidatePipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor.CandidatePipelineEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.StoppedGateEtwitterception
import com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor.PipelineResultSideEffectEtwitterecutor
import com.twitter.product_mitwitterer.core.service.quality_factor_etwitterecutor.QualityFactorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.scoring_pipeline_etwitterecutor.ScoringPipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.scoring_pipeline_etwitterecutor.ScoringPipelineEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor
import com.twitter.stitch.Arrow

/**
 * RecommendationPipelineBuilder builds [[RecommendationPipeline]]s from [[RecommendationPipelineConfig]]s.
 *
 * You should inject a [[RecommendationPipelineBuilderFactory]] and call `.get` to build these.
 *
 * @see [[RecommendationPipelineConfig]] for the description of the type parameters.
 *
 * @note Almost a mirror of MitwittererPipelineBuilder
 */

class RecommendationPipelineBuilder[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any],
  DomainResultType <: HasMarshalling,
  Result
](
  candidatePipelineEtwitterecutor: CandidatePipelineEtwitterecutor,
  gateEtwitterecutor: GateEtwitterecutor,
  selectorEtwitterecutor: SelectorEtwitterecutor,
  queryFeatureHydratorEtwitterecutor: QueryFeatureHydratorEtwitterecutor,
  asyncFeatureMapEtwitterecutor: AsyncFeatureMapEtwitterecutor,
  candidateFeatureHydratorEtwitterecutor: CandidateFeatureHydratorEtwitterecutor,
  filterEtwitterecutor: FilterEtwitterecutor,
  scoringPipelineEtwitterecutor: ScoringPipelineEtwitterecutor,
  candidateDecoratorEtwitterecutor: CandidateDecoratorEtwitterecutor,
  domainMarshallerEtwitterecutor: DomainMarshallerEtwitterecutor,
  transportMarshallerEtwitterecutor: TransportMarshallerEtwitterecutor,
  pipelineResultSideEffectEtwitterecutor: PipelineResultSideEffectEtwitterecutor,
  candidatePipelineBuilderFactory: CandidatePipelineBuilderFactory,
  scoringPipelineBuilderFactory: ScoringPipelineBuilderFactory,
  override val statsReceiver: StatsReceiver)
    etwittertends PipelineBuilder[Query]
    with Logging {

  override type UnderlyingResultType = Result
  override type PipelineResultType = RecommendationPipelineResult[Candidate, Result]

  def qualityFactorStep(
    qualityFactorStatus: QualityFactorStatus
  ): Step[Query, QualityFactorEtwitterecutorResult] =
    new Step[Query, QualityFactorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier =
        RecommendationPipelineConfig.qualityFactorStep

      override def etwitterecutorArrow: Arrow[Query, QualityFactorEtwitterecutorResult] =
        Arrow
          .map[Query, QualityFactorEtwitterecutorResult] { _ =>
            QualityFactorEtwitterecutorResult(
              pipelineQualityFactors =
                qualityFactorStatus.qualityFactorByPipeline.mapValues(_.currentValue)
            )
          }

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): Query = query

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: QualityFactorEtwitterecutorResult
      ): RecommendationPipelineResult[Candidate, Result] =
        previousPipelineResult.copy(qualityFactorResult = Some(etwitterecutorResult))

      override def queryUpdater(
        query: Query,
        etwitterecutorResult: QualityFactorEtwitterecutorResult
      ): Query = {
        query match {
          case queryWithQualityFactor: HasQualityFactorStatus =>
            queryWithQualityFactor
              .withQualityFactorStatus(
                queryWithQualityFactor.qualityFactorStatus.getOrElse(QualityFactorStatus.empty) ++
                  qualityFactorStatus
              ).asInstanceOf[Query]
          case _ =>
            query
        }
      }
    }

  def gatesStep(
    gates: Seq[Gate[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[Query, GateEtwitterecutorResult] = new Step[Query, GateEtwitterecutorResult] {
    override def identifier: PipelineStepIdentifier = RecommendationPipelineConfig.gatesStep

    override def etwitterecutorArrow: Arrow[Query, GateEtwitterecutorResult] =
      gateEtwitterecutor.arrow(gates, contetwittert)

    override def inputAdaptor(
      query: Query,
      previousResult: RecommendationPipelineResult[Candidate, Result]
    ): Query =
      query

    override def resultUpdater(
      previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
      etwitterecutorResult: GateEtwitterecutorResult
    ): RecommendationPipelineResult[Candidate, Result] =
      previousPipelineResult.copy(gateResult = Some(etwitterecutorResult))
  }

  def fetchQueryFeaturesStep(
    queryFeatureHydrators: Seq[BaseQueryFeatureHydrator[Query, _]],
    stepIdentifier: PipelineStepIdentifier,
    updater: ResultUpdater[
      RecommendationPipelineResult[Candidate, Result],
      QueryFeatureHydratorEtwitterecutor.Result
    ],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[Query, QueryFeatureHydratorEtwitterecutor.Result] =
    new Step[Query, QueryFeatureHydratorEtwitterecutor.Result] {
      override def identifier: PipelineStepIdentifier = stepIdentifier

      override def etwitterecutorArrow: Arrow[Query, QueryFeatureHydratorEtwitterecutor.Result] =
        queryFeatureHydratorEtwitterecutor.arrow(
          queryFeatureHydrators,
          RecommendationPipelineConfig.stepsAsyncFeatureHydrationCanBeCompletedBy,
          contetwittert)

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): Query = query

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: QueryFeatureHydratorEtwitterecutor.Result
      ): RecommendationPipelineResult[Candidate, Result] =
        updater(previousPipelineResult, etwitterecutorResult)

      override def queryUpdater(
        query: Query,
        etwitterecutorResult: QueryFeatureHydratorEtwitterecutor.Result
      ): Query =
        query
          .withFeatureMap(
            query.features
              .getOrElse(FeatureMap.empty) ++ etwitterecutorResult.featureMap).asInstanceOf[Query]
    }

  def asyncFeaturesStep(
    stepToHydrateFor: PipelineStepIdentifier,
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] =
    new Step[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] {
      override def identifier: PipelineStepIdentifier =
        RecommendationPipelineConfig.asyncFeaturesStep(stepToHydrateFor)

      override def etwitterecutorArrow: Arrow[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] =
        asyncFeatureMapEtwitterecutor.arrow(
          stepToHydrateFor,
          identifier,
          contetwittert
        )

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): AsyncFeatureMap =
        previousResult.mergedAsyncQueryFeatures
          .getOrElse(
            throw InvalidStepStateEtwitterception(identifier, "MergedAsyncQueryFeatures")
          )

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: AsyncFeatureMapEtwitterecutorResults
      ): RecommendationPipelineResult[Candidate, Result] =
        previousPipelineResult.copy(
          asyncFeatureHydrationResults = previousPipelineResult.asyncFeatureHydrationResults match {
            case Some(etwitteristingResults) => Some(etwitteristingResults ++ etwitterecutorResult)
            case None => Some(etwitterecutorResult)
          })

      override def queryUpdater(
        query: Query,
        etwitterecutorResult: AsyncFeatureMapEtwitterecutorResults
      ): Query =
        if (etwitterecutorResult.featureMapsByStep
            .getOrElse(stepToHydrateFor, FeatureMap.empty).isEmpty) {
          query
        } else {
          query
            .withFeatureMap(
              query.features
                .getOrElse(FeatureMap.empty) ++ etwitterecutorResult.featureMapsByStep(
                stepToHydrateFor)).asInstanceOf[Query]
        }
    }

  def candidatePipelinesStep(
    candidatePipelines: Seq[CandidatePipeline[Query]],
    defaultFailOpenPolicy: FailOpenPolicy,
    failOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy],
    qualityFactorObserverByPipeline: Map[ComponentIdentifier, QualityFactorObserver],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[CandidatePipeline.Inputs[Query], CandidatePipelineEtwitterecutorResult] =
    new Step[CandidatePipeline.Inputs[Query], CandidatePipelineEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier =
        RecommendationPipelineConfig.candidatePipelinesStep

      override def etwitterecutorArrow: Arrow[CandidatePipeline.Inputs[
        Query
      ], CandidatePipelineEtwitterecutorResult] =
        candidatePipelineEtwitterecutor
          .arrow(
            candidatePipelines,
            defaultFailOpenPolicy,
            failOpenPolicies,
            qualityFactorObserverByPipeline,
            contetwittert
          )

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): CandidatePipeline.Inputs[
        Query
      ] = CandidatePipeline.Inputs(query, Seq.empty)

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: CandidatePipelineEtwitterecutorResult
      ): RecommendationPipelineResult[Candidate, Result] =
        previousPipelineResult.copy(candidatePipelineResults = Some(etwitterecutorResult))

      override def queryUpdater(
        query: Query,
        etwitterecutorResult: CandidatePipelineEtwitterecutorResult
      ): Query = {
        val updatedFeatureMap = query.features
          .getOrElse(FeatureMap.empty) ++ etwitterecutorResult.queryFeatureMap
        query
          .withFeatureMap(updatedFeatureMap).asInstanceOf[Query]
      }
    }

  def dependentCandidatePipelinesStep(
    candidatePipelines: Seq[CandidatePipeline[Query]],
    defaultFailOpenPolicy: FailOpenPolicy,
    failOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy],
    qualityFactorObserverByPipeline: Map[ComponentIdentifier, QualityFactorObserver],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[CandidatePipeline.Inputs[Query], CandidatePipelineEtwitterecutorResult] =
    new Step[CandidatePipeline.Inputs[Query], CandidatePipelineEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier =
        RecommendationPipelineConfig.dependentCandidatePipelinesStep

      override def etwitterecutorArrow: Arrow[CandidatePipeline.Inputs[
        Query
      ], CandidatePipelineEtwitterecutorResult] =
        candidatePipelineEtwitterecutor
          .arrow(
            candidatePipelines,
            defaultFailOpenPolicy,
            failOpenPolicies,
            qualityFactorObserverByPipeline,
            contetwittert
          )

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): CandidatePipeline.Inputs[
        Query
      ] = {
        val previousCandidates = previousResult.candidatePipelineResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "Candidates")
          }.candidatePipelineResults.flatMap(_.result.getOrElse(Seq.empty))

        CandidatePipeline.Inputs(query, previousCandidates)
      }

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: CandidatePipelineEtwitterecutorResult
      ): RecommendationPipelineResult[Candidate, Result] =
        previousPipelineResult.copy(dependentCandidatePipelineResults = Some(etwitterecutorResult))

      override def queryUpdater(
        query: Query,
        etwitterecutorResult: CandidatePipelineEtwitterecutorResult
      ): Query = {
        val updatedFeatureMap = query.features
          .getOrElse(FeatureMap.empty) ++ etwitterecutorResult.queryFeatureMap
        query
          .withFeatureMap(updatedFeatureMap).asInstanceOf[Query]
      }
    }

  abstract class FilterStep(
    filters: Seq[Filter[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert,
    override val identifier: PipelineStepIdentifier)
      etwittertends Step[
        (Query, Seq[CandidateWithFeatures[Candidate]]),
        FilterEtwitterecutorResult[Candidate]
      ] {

    def itemCandidates(
      previousResult: RecommendationPipelineResult[Candidate, Result]
    ): Seq[CandidateWithDetails]

    override def etwitterecutorArrow: Arrow[
      (Query, Seq[CandidateWithFeatures[Candidate]]),
      FilterEtwitterecutorResult[Candidate]
    ] =
      filterEtwitterecutor.arrow(filters, contetwittert)

    override def inputAdaptor(
      query: Query,
      previousResult: RecommendationPipelineResult[Candidate, Result]
    ): (Query, Seq[CandidateWithFeatures[Candidate]]) = {

      val etwittertractedItemCandidates = itemCandidates(previousResult).collect {
        case itemCandidate: ItemCandidateWithDetails => itemCandidate
      }

      (query, etwittertractedItemCandidates.asInstanceOf[Seq[CandidateWithFeatures[Candidate]]])
    }
  }

  def postCandidatePipelinesSelectorStep(
    selectors: Seq[Selector[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] =
    new Step[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier =
        RecommendationPipelineConfig.postCandidatePipelinesSelectorsStep

      override def etwitterecutorArrow: Arrow[SelectorEtwitterecutor.Inputs[
        Query
      ], SelectorEtwitterecutorResult] =
        selectorEtwitterecutor.arrow(selectors, contetwittert)

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): SelectorEtwitterecutor.Inputs[Query] = {
        val candidatePipelineResults = previousResult.candidatePipelineResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "CandidatePipelineResults")
          }.candidatePipelineResults.flatMap(_.result.getOrElse(Seq.empty))
        val dependentCandidatePipelineResults = previousResult.dependentCandidatePipelineResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "DependentCandidatePipelineResults")
          }.candidatePipelineResults.flatMap(_.result.getOrElse(Seq.empty))

        SelectorEtwitterecutor.Inputs(
          query = query,
          candidatesWithDetails = candidatePipelineResults ++ dependentCandidatePipelineResults
        )
      }

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: SelectorEtwitterecutorResult
      ): RecommendationPipelineResult[Candidate, Result] =
        previousPipelineResult.copy(postCandidatePipelinesSelectorResults = Some(etwitterecutorResult))
    }

  def postCandidatePipelinesFeatureHydrationStep(
    hydrators: Seq[BaseCandidateFeatureHydrator[Query, Candidate, _]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[
    CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
    CandidateFeatureHydratorEtwitterecutorResult[Candidate]
  ] = new Step[
    CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
    CandidateFeatureHydratorEtwitterecutorResult[Candidate]
  ] {
    override def identifier: PipelineStepIdentifier =
      RecommendationPipelineConfig.postCandidatePipelinesFeatureHydrationStep

    override def etwitterecutorArrow: Arrow[
      CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate],
      CandidateFeatureHydratorEtwitterecutorResult[Candidate]
    ] =
      candidateFeatureHydratorEtwitterecutor.arrow(hydrators, contetwittert)

    override def inputAdaptor(
      query: Query,
      previousResult: RecommendationPipelineResult[Candidate, Result]
    ): CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Candidate] = {
      val selectedCandidatesResult =
        previousResult.postCandidatePipelinesSelectorResults.getOrElse {
          throw InvalidStepStateEtwitterception(identifier, "PostCandidatePipelinesSelectorResults")
        }.selectedCandidates

      CandidateFeatureHydratorEtwitterecutor.Inputs(
        query,
        selectedCandidatesResult.asInstanceOf[Seq[CandidateWithFeatures[Candidate]]])
    }

    override def resultUpdater(
      previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
      etwitterecutorResult: CandidateFeatureHydratorEtwitterecutorResult[Candidate]
    ): RecommendationPipelineResult[Candidate, Result] = previousPipelineResult.copy(
      postCandidatePipelinesFeatureHydrationResults = Some(etwitterecutorResult)
    )
  }

  def globalFiltersStep(
    filters: Seq[Filter[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[(Query, Seq[CandidateWithFeatures[Candidate]]), FilterEtwitterecutorResult[Candidate]] =
    new FilterStep(filters, contetwittert, RecommendationPipelineConfig.globalFiltersStep) {
      override def itemCandidates(
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): Seq[CandidateWithDetails] = {
        val candidates = previousResult.postCandidatePipelinesSelectorResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "PostCandidatePipelineSelectorResults")
          }.selectedCandidates.collect {
            case itemCandidate: ItemCandidateWithDetails => itemCandidate
          }

        val featureMaps = previousResult.postCandidatePipelinesFeatureHydrationResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(
              identifier,
              "PostCandidatePipelineFeatureHydrationResults")
          }.results.map(_.features)
        // If no hydrators were run, this list would be empty. Otherwise, order and cardinality is
        // always ensured to match.
        if (featureMaps.isEmpty) {
          candidates
        } else {
          candidates.zip(featureMaps).map {
            case (candidate, featureMap) =>
              candidate.copy(features = candidate.features ++ featureMap)
          }
        }
      }

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: FilterEtwitterecutorResult[Candidate]
      ): RecommendationPipelineResult[Candidate, Result] = previousPipelineResult.copy(
        globalFilterResults = Some(etwitterecutorResult)
      )
    }

  def scoringPipelinesStep(
    scoringPipelines: Seq[ScoringPipeline[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert,
    defaultFailOpenPolicy: FailOpenPolicy,
    failOpenPolicies: Map[ScoringPipelineIdentifier, FailOpenPolicy],
    qualityFactorObserverByPipeline: Map[ComponentIdentifier, QualityFactorObserver]
  ): Step[ScoringPipelineEtwitterecutor.Inputs[Query], ScoringPipelineEtwitterecutorResult[
    Candidate
  ]] =
    new Step[ScoringPipelineEtwitterecutor.Inputs[Query], ScoringPipelineEtwitterecutorResult[
      Candidate
    ]] {
      override def identifier: PipelineStepIdentifier =
        RecommendationPipelineConfig.scoringPipelinesStep

      override def etwitterecutorArrow: Arrow[
        ScoringPipelineEtwitterecutor.Inputs[Query],
        ScoringPipelineEtwitterecutorResult[Candidate]
      ] = scoringPipelineEtwitterecutor.arrow(
        scoringPipelines,
        contetwittert,
        defaultFailOpenPolicy,
        failOpenPolicies,
        qualityFactorObserverByPipeline
      )

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): ScoringPipelineEtwitterecutor.Inputs[Query] = {
        val selectedCandidates =
          previousResult.postCandidatePipelinesSelectorResults.getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "PostCandidatePipelinesSelectorResults")
          }.selectedCandidates

        val itemCandidates = selectedCandidates.collect {
          case itemCandidate: ItemCandidateWithDetails => itemCandidate
        }

        val featureMaps = previousResult.postCandidatePipelinesFeatureHydrationResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(
              identifier,
              "PostCandidatePipelineFeatureHydrationResults")
          }.results.map(_.features)
        // If no hydrators were run, this list would be empty. Otherwise, order and cardinality is
        // always ensured to match.
        val updatedCandidates = if (featureMaps.isEmpty) {
          itemCandidates
        } else {
          itemCandidates.zip(featureMaps).map {
            case (candidate, featureMap) =>
              candidate.copy(features = candidate.features ++ featureMap)
          }
        }

        // Filter the original list of candidates to keep only the ones that were kept from
        // filtering
        val filterResults: Set[Candidate] = previousResult.globalFilterResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "FilterResults")
          }.result.toSet

        val filteredItemCandidates = updatedCandidates.filter { itemCandidate =>
          filterResults.contains(itemCandidate.candidate.asInstanceOf[Candidate])
        }

        ScoringPipelineEtwitterecutor.Inputs(
          query,
          filteredItemCandidates
        )
      }

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: ScoringPipelineEtwitterecutorResult[Candidate]
      ): RecommendationPipelineResult[Candidate, Result] = previousPipelineResult
        .copy(scoringPipelineResults = Some(etwitterecutorResult))
    }

  def resultSelectorsStep(
    selectors: Seq[Selector[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] =
    new Step[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier =
        RecommendationPipelineConfig.resultSelectorsStep

      override def etwitterecutorArrow: Arrow[SelectorEtwitterecutor.Inputs[
        Query
      ], SelectorEtwitterecutorResult] =
        selectorEtwitterecutor.arrow(selectors, contetwittert)

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): SelectorEtwitterecutor.Inputs[Query] = {

        /**
         * See [[ScoringPipelineEtwitterecutor]], scoringPipelineResults contains the fully re-merged
         * and updated FeatureMap so there's no need to do any recomposition. Scoring Pipeline Results
         * has only candidates that were kept in previous filtering, with their final merged feature
         * map.
         */
        val scorerResults = previousResult.scoringPipelineResults.getOrElse {
          throw InvalidStepStateEtwitterception(identifier, "Scores")
        }

        SelectorEtwitterecutor.Inputs(
          query = query,
          candidatesWithDetails = scorerResults.result
        )
      }

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: SelectorEtwitterecutorResult
      ): RecommendationPipelineResult[Candidate, Result] =
        previousPipelineResult.copy(resultSelectorResults = Some(etwitterecutorResult))
    }

  def postSelectionFiltersStep(
    filters: Seq[Filter[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[(Query, Seq[CandidateWithFeatures[Candidate]]), FilterEtwitterecutorResult[Candidate]] =
    new FilterStep(filters, contetwittert, RecommendationPipelineConfig.postSelectionFiltersStep) {

      override def itemCandidates(
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): Seq[CandidateWithDetails] = {
        previousResult.resultSelectorResults.getOrElse {
          throw InvalidStepStateEtwitterception(identifier, "Candidates")
        }.selectedCandidates
      }

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: FilterEtwitterecutorResult[Candidate]
      ): RecommendationPipelineResult[Candidate, Result] = {
        previousPipelineResult.copy(postSelectionFilterResults = Some(etwitterecutorResult))
      }
    }

  def decoratorStep(
    decorator: Option[CandidateDecorator[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[(Query, Seq[CandidateWithFeatures[Candidate]]), CandidateDecoratorEtwitterecutorResult] =
    new Step[(Query, Seq[CandidateWithFeatures[Candidate]]), CandidateDecoratorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = RecommendationPipelineConfig.decoratorStep

      override lazy val etwitterecutorArrow: Arrow[
        (Query, Seq[CandidateWithFeatures[Candidate]]),
        CandidateDecoratorEtwitterecutorResult
      ] =
        candidateDecoratorEtwitterecutor.arrow(decorator, contetwittert)

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): (Query, Seq[CandidateWithFeatures[Candidate]]) = {

        val selectorResults = previousResult.resultSelectorResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "SelectorResults")
          }.selectedCandidates
          .collect { case candidate: ItemCandidateWithDetails => candidate }

        val filterResults = previousResult.postSelectionFilterResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "PostSelectionFilterResults")
          }.result.toSet

        val itemCandidateWithDetailsPostFiltering =
          selectorResults
            .filter(candidateWithDetails =>
              filterResults.contains(
                candidateWithDetails.candidate
                  .asInstanceOf[Candidate]))
            .asInstanceOf[Seq[CandidateWithFeatures[Candidate]]]

        (query, itemCandidateWithDetailsPostFiltering)
      }

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: CandidateDecoratorEtwitterecutorResult
      ): RecommendationPipelineResult[Candidate, Result] =
        previousPipelineResult.copy(
          candidateDecoratorResult = Some(etwitterecutorResult)
        )
    }

  def domainMarshallingStep(
    domainMarshaller: DomainMarshaller[Query, DomainResultType],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[DomainMarshallerEtwitterecutor.Inputs[Query], DomainMarshallerEtwitterecutor.Result[
    DomainResultType
  ]] =
    new Step[DomainMarshallerEtwitterecutor.Inputs[Query], DomainMarshallerEtwitterecutor.Result[
      DomainResultType
    ]] {
      override def identifier: PipelineStepIdentifier =
        RecommendationPipelineConfig.domainMarshallerStep

      override def etwitterecutorArrow: Arrow[
        DomainMarshallerEtwitterecutor.Inputs[Query],
        DomainMarshallerEtwitterecutor.Result[DomainResultType]
      ] =
        domainMarshallerEtwitterecutor.arrow(domainMarshaller, contetwittert)

      override def inputAdaptor(
        query: Query,
        previousResult: RecommendationPipelineResult[Candidate, Result]
      ): DomainMarshallerEtwitterecutor.Inputs[Query] = {
        val selectorResults = previousResult.resultSelectorResults.getOrElse {
          throw InvalidStepStateEtwitterception(identifier, "SelectorResults")
        }

        val filterResults = previousResult.postSelectionFilterResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "PostSelectionFilterResults")
          }.result.toSet

        val filteredResults = selectorResults.selectedCandidates.collect {
          case candidate: ItemCandidateWithDetails
              if filterResults.contains(candidate.candidate.asInstanceOf[Candidate]) =>
            candidate
        }

        val decoratorResults = previousResult.candidateDecoratorResult
          .getOrElse(throw InvalidStepStateEtwitterception(identifier, "DecoratorStep")).result.map {
            decoration =>
              decoration.candidate -> decoration.presentation
          }.toMap

        val finalResults = filteredResults.map { itemWithDetails =>
          decoratorResults.get(itemWithDetails.candidate) match {
            case Some(presentation: ItemPresentation) =>
              if (itemWithDetails.presentation.isDefined) {
                throw PipelineFailure(
                  category = MisconfiguredDecorator,
                  reason = "Item Candidate already decorated",
                  componentStack = Some(contetwittert.componentStack))
              } else {
                itemWithDetails.copy(presentation = Some(presentation))
              }
            case Some(_) =>
              throw PipelineFailure(
                category = MisconfiguredDecorator,
                reason = "Item Candidate got back a non ItemPresentation from decorator",
                componentStack = Some(contetwittert.componentStack))
            case None => itemWithDetails
          }
        }
        DomainMarshallerEtwitterecutor.Inputs(
          query = query,
          candidatesWithDetails = finalResults
        )
      }

      override def resultUpdater(
        previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
        etwitterecutorResult: DomainMarshallerEtwitterecutor.Result[DomainResultType]
      ): RecommendationPipelineResult[Candidate, Result] = previousPipelineResult.copy(
        domainMarshallerResults = Some(etwitterecutorResult)
      )
    }

  def resultSideEffectsStep(
    sideEffects: Seq[PipelineResultSideEffect[Query, DomainResultType]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[
    PipelineResultSideEffect.Inputs[Query, DomainResultType],
    PipelineResultSideEffectEtwitterecutor.Result
  ] = new Step[
    PipelineResultSideEffect.Inputs[Query, DomainResultType],
    PipelineResultSideEffectEtwitterecutor.Result
  ] {
    override def identifier: PipelineStepIdentifier =
      RecommendationPipelineConfig.resultSideEffectsStep

    override def etwitterecutorArrow: Arrow[
      PipelineResultSideEffect.Inputs[Query, DomainResultType],
      PipelineResultSideEffectEtwitterecutor.Result
    ] = pipelineResultSideEffectEtwitterecutor.arrow(sideEffects, contetwittert)

    override def inputAdaptor(
      query: Query,
      previousResult: RecommendationPipelineResult[Candidate, Result]
    ): PipelineResultSideEffect.Inputs[Query, DomainResultType] = {

      // Re-apply decorations to the selected results
      val resultSelectorResults = {
        val decoratorResults = previousResult.candidateDecoratorResult
          .getOrElse(throw InvalidStepStateEtwitterception(identifier, "DecoratorStep")).result.map {
            decoration =>
              decoration.candidate -> decoration.presentation
          }.toMap

        val previousSelectorResults = previousResult.resultSelectorResults.getOrElse {
          throw InvalidStepStateEtwitterception(identifier, "SelectorResults")
        }

        val filterResults = previousResult.postSelectionFilterResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "PostSelectionFilterResults")
          }.result.toSet

        val filteredSelectorResults = previousSelectorResults.selectedCandidates.collect {
          case candidate: ItemCandidateWithDetails
              if filterResults.contains(candidate.candidate.asInstanceOf[Candidate]) =>
            candidate
        }

        val decoratedSelectedResults = filteredSelectorResults.map {
          case itemWithDetails: ItemCandidateWithDetails =>
            decoratorResults.get(itemWithDetails.candidate) match {
              case Some(presentation: ItemPresentation) =>
                if (itemWithDetails.presentation.isDefined) {
                  throw PipelineFailure(
                    category = MisconfiguredDecorator,
                    reason = "Item Candidate already decorated",
                    componentStack = Some(contetwittert.componentStack))
                } else {
                  itemWithDetails.copy(presentation = Some(presentation))
                }
              case Some(_) =>
                throw PipelineFailure(
                  category = MisconfiguredDecorator,
                  reason = "Item Candidate got back a non ItemPresentation from decorator",
                  componentStack = Some(contetwittert.componentStack))
              case None => itemWithDetails
            }
          case item =>
            // This branch should be impossible to hit since we do a .collect on ItemCandidateWithDetails
            // as part of etwitterecuting the candidate pipelines.
            throw PipelineFailure(
              category = IllegalStateFailure,
              reason =
                s"Only ItemCandidateWithDetails etwitterpected in pipeline, found: ${item.toString}",
              componentStack = Some(contetwittert.componentStack)
            )
        }

        previousSelectorResults.copy(selectedCandidates = decoratedSelectedResults)
      }

      val domainMarshallerResults = previousResult.domainMarshallerResults.getOrElse {
        throw InvalidStepStateEtwitterception(identifier, "DomainMarshallerResults")
      }

      PipelineResultSideEffect.Inputs[Query, DomainResultType](
        query = query,
        selectedCandidates = resultSelectorResults.selectedCandidates,
        remainingCandidates = resultSelectorResults.remainingCandidates,
        droppedCandidates = resultSelectorResults.droppedCandidates,
        response = domainMarshallerResults.result.asInstanceOf[DomainResultType]
      )
    }

    override def resultUpdater(
      previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
      etwitterecutorResult: PipelineResultSideEffectEtwitterecutor.Result
    ): RecommendationPipelineResult[Candidate, Result] =
      previousPipelineResult.copy(resultSideEffectResults = Some(etwitterecutorResult))
  }

  def transportMarshallingStep(
    transportMarshaller: TransportMarshaller[DomainResultType, Result],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[
    TransportMarshallerEtwitterecutor.Inputs[DomainResultType],
    TransportMarshallerEtwitterecutor.Result[Result]
  ] = new Step[TransportMarshallerEtwitterecutor.Inputs[
    DomainResultType
  ], TransportMarshallerEtwitterecutor.Result[Result]] {
    override def identifier: PipelineStepIdentifier =
      RecommendationPipelineConfig.transportMarshallerStep

    override def etwitterecutorArrow: Arrow[TransportMarshallerEtwitterecutor.Inputs[
      DomainResultType
    ], TransportMarshallerEtwitterecutor.Result[Result]] =
      transportMarshallerEtwitterecutor.arrow(transportMarshaller, contetwittert)

    override def inputAdaptor(
      query: Query,
      previousResult: RecommendationPipelineResult[Candidate, Result]
    ): TransportMarshallerEtwitterecutor.Inputs[DomainResultType] = {
      val domainMarshallingResults = previousResult.domainMarshallerResults.getOrElse {
        throw InvalidStepStateEtwitterception(identifier, "DomainMarshallerResults")
      }

      // Since the PipelineResult just uses HasMarshalling
      val domainResult = domainMarshallingResults.result.asInstanceOf[DomainResultType]

      TransportMarshallerEtwitterecutor.Inputs(domainResult)
    }

    override def resultUpdater(
      previousPipelineResult: RecommendationPipelineResult[Candidate, Result],
      etwitterecutorResult: TransportMarshallerEtwitterecutor.Result[Result]
    ): RecommendationPipelineResult[Candidate, Result] = previousPipelineResult.copy(
      transportMarshallerResults = Some(etwitterecutorResult),
      result = Some(etwitterecutorResult.result)
    )
  }

  def build(
    parentComponentIdentifierStack: ComponentIdentifierStack,
    config: RecommendationPipelineConfig[
      Query,
      Candidate,
      DomainResultType,
      Result
    ]
  ): RecommendationPipeline[Query, Candidate, Result] = {
    val pipelineIdentifier = config.identifier

    val contetwittert = Etwitterecutor.Contetwittert(
      PipelineFailureClassifier(
        config.failureClassifier.orElse(StoppedGateEtwitterception.classifier(ProductDisabled))),
      parentComponentIdentifierStack.push(pipelineIdentifier)
    )

    val decorator = config.decorator.map(decorator =>
      CandidateDecorator.copyWithUpdatedIdentifier(decorator, pipelineIdentifier))

    val qualityFactorStatus: QualityFactorStatus =
      QualityFactorStatus.build(config.qualityFactorConfigs)

    val qualityFactorObserverByPipeline =
      qualityFactorStatus.qualityFactorByPipeline.mapValues { qualityFactor =>
        qualityFactor.buildObserver()
      }

    buildGaugesForQualityFactor(pipelineIdentifier, qualityFactorStatus, statsReceiver)

    val candidatePipelines: Seq[CandidatePipeline[Query]] = config.candidatePipelines.map {
      pipelineConfig: CandidatePipelineConfig[Query, _, _, _] =>
        pipelineConfig.build(contetwittert.componentStack, candidatePipelineBuilderFactory)
    }

    val dependentCandidatePipelines: Seq[CandidatePipeline[Query]] =
      config.dependentCandidatePipelines.map {
        pipelineConfig: DependentCandidatePipelineConfig[Query, _, _, _] =>
          pipelineConfig.build(contetwittert.componentStack, candidatePipelineBuilderFactory)
      }

    val scoringPipelines: Seq[ScoringPipeline[Query, Candidate]] = config.scoringPipelines.map {
      pipelineConfig: ScoringPipelineConfig[Query, Candidate] =>
        pipelineConfig.build(contetwittert.componentStack, scoringPipelineBuilderFactory)
    }

    val builtSteps = Seq(
      qualityFactorStep(qualityFactorStatus),
      gatesStep(config.gates, contetwittert),
      fetchQueryFeaturesStep(
        config.fetchQueryFeatures,
        RecommendationPipelineConfig.fetchQueryFeaturesStep,
        (previousPipelineResult, etwitterecutorResult) =>
          previousPipelineResult.copy(queryFeatures = Some(etwitterecutorResult)),
        contetwittert
      ),
      fetchQueryFeaturesStep(
        config.fetchQueryFeaturesPhase2,
        RecommendationPipelineConfig.fetchQueryFeaturesPhase2Step,
        (previousPipelineResult, etwitterecutorResult) =>
          previousPipelineResult.copy(
            queryFeaturesPhase2 = Some(etwitterecutorResult),
            mergedAsyncQueryFeatures = Some(
              previousPipelineResult.queryFeatures
                .getOrElse(throw InvalidStepStateEtwitterception(
                  RecommendationPipelineConfig.fetchQueryFeaturesPhase2Step,
                  "QueryFeatures"))
                .asyncFeatureMap ++ etwitterecutorResult.asyncFeatureMap)
          ),
        contetwittert
      ),
      asyncFeaturesStep(RecommendationPipelineConfig.candidatePipelinesStep, contetwittert),
      candidatePipelinesStep(
        candidatePipelines,
        config.defaultFailOpenPolicy,
        config.candidatePipelineFailOpenPolicies,
        qualityFactorObserverByPipeline,
        contetwittert),
      asyncFeaturesStep(RecommendationPipelineConfig.dependentCandidatePipelinesStep, contetwittert),
      dependentCandidatePipelinesStep(
        dependentCandidatePipelines,
        config.defaultFailOpenPolicy,
        config.candidatePipelineFailOpenPolicies,
        qualityFactorObserverByPipeline,
        contetwittert),
      asyncFeaturesStep(RecommendationPipelineConfig.postCandidatePipelinesSelectorsStep, contetwittert),
      postCandidatePipelinesSelectorStep(config.postCandidatePipelinesSelectors, contetwittert),
      asyncFeaturesStep(
        RecommendationPipelineConfig.postCandidatePipelinesFeatureHydrationStep,
        contetwittert),
      postCandidatePipelinesFeatureHydrationStep(
        config.postCandidatePipelinesFeatureHydration,
        contetwittert),
      asyncFeaturesStep(RecommendationPipelineConfig.globalFiltersStep, contetwittert),
      globalFiltersStep(config.globalFilters, contetwittert),
      asyncFeaturesStep(RecommendationPipelineConfig.scoringPipelinesStep, contetwittert),
      scoringPipelinesStep(
        scoringPipelines,
        contetwittert,
        config.defaultFailOpenPolicy,
        config.scoringPipelineFailOpenPolicies,
        qualityFactorObserverByPipeline
      ),
      asyncFeaturesStep(RecommendationPipelineConfig.resultSelectorsStep, contetwittert),
      resultSelectorsStep(config.resultSelectors, contetwittert),
      asyncFeaturesStep(RecommendationPipelineConfig.postSelectionFiltersStep, contetwittert),
      postSelectionFiltersStep(config.postSelectionFilters, contetwittert),
      asyncFeaturesStep(RecommendationPipelineConfig.decoratorStep, contetwittert),
      decoratorStep(decorator, contetwittert),
      domainMarshallingStep(config.domainMarshaller, contetwittert),
      asyncFeaturesStep(RecommendationPipelineConfig.resultSideEffectsStep, contetwittert),
      resultSideEffectsStep(config.resultSideEffects, contetwittert),
      transportMarshallingStep(config.transportMarshaller, contetwittert)
    )

    val finalArrow = buildCombinedArrowFromSteps(
      steps = builtSteps,
      contetwittert = contetwittert,
      initialEmptyResult = RecommendationPipelineResult.empty,
      stepsInOrderFromConfig = RecommendationPipelineConfig.stepsInOrder
    )

    val configFromBuilder = config
    new RecommendationPipeline[Query, Candidate, Result] {
      override private[core] val config: RecommendationPipelineConfig[
        Query,
        Candidate,
        _,
        Result
      ] =
        configFromBuilder
      override val arrow: Arrow[Query, RecommendationPipelineResult[Candidate, Result]] =
        finalArrow
      override val identifier: RecommendationPipelineIdentifier = pipelineIdentifier
      override val alerts: Seq[Alert] = config.alerts
      override val children: Seq[Component] =
        config.gates ++
          config.fetchQueryFeatures ++
          candidatePipelines ++
          dependentCandidatePipelines ++
          config.postCandidatePipelinesFeatureHydration ++
          config.globalFilters ++
          scoringPipelines ++
          config.postSelectionFilters ++
          config.resultSideEffects ++
          decorator.toSeq ++
          Seq(config.domainMarshaller, config.transportMarshaller)
    }
  }
}
