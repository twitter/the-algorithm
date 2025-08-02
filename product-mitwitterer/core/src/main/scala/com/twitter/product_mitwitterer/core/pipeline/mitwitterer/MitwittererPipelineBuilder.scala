package com.twitter.product_mitwitterer.core.pipeline.mitwitterer

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.model.common.Component
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack
import com.twitter.product_mitwitterer.core.model.common.identifier.MitwittererPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.FailOpenPolicy
import com.twitter.product_mitwitterer.core.pipeline.InvalidStepStateEtwitterception
import com.twitter.product_mitwitterer.core.pipeline.PipelineBuilder
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipeline
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineBuilderFactory
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureClassifier
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ProductDisabled
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorObserver
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorStatus
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutor
import com.twitter.product_mitwitterer.core.service.async_feature_map_etwitterecutor.AsyncFeatureMapEtwitterecutorResults
import com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor.CandidatePipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor.CandidatePipelineEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.StoppedGateEtwitterception
import com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor.PipelineResultSideEffectEtwitterecutor
import com.twitter.product_mitwitterer.core.service.quality_factor_etwitterecutor.QualityFactorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor
import com.twitter.stitch.Arrow
import com.twitter.util.logging.Logging

/**
 * MitwittererPipelineBuilder builds [[MitwittererPipeline]]s from [[MitwittererPipelineConfig]]s.
 *
 * You should inject a [[MitwittererPipelineBuilderFactory]] and call `.get` to build these.
 *
 * @see [[MitwittererPipelineConfig]] for the description of the type parameters
 */
class MitwittererPipelineBuilder[Query <: PipelineQuery, DomainResultType <: HasMarshalling, Result](
  candidatePipelineEtwitterecutor: CandidatePipelineEtwitterecutor,
  gateEtwitterecutor: GateEtwitterecutor,
  selectorEtwitterecutor: SelectorEtwitterecutor,
  queryFeatureHydratorEtwitterecutor: QueryFeatureHydratorEtwitterecutor,
  asyncFeatureMapEtwitterecutor: AsyncFeatureMapEtwitterecutor,
  domainMarshallerEtwitterecutor: DomainMarshallerEtwitterecutor,
  transportMarshallerEtwitterecutor: TransportMarshallerEtwitterecutor,
  pipelineResultSideEffectEtwitterecutor: PipelineResultSideEffectEtwitterecutor,
  candidatePipelineBuilderFactory: CandidatePipelineBuilderFactory,
  override val statsReceiver: StatsReceiver)
    etwittertends PipelineBuilder[Query]
    with Logging {

  override type UnderlyingResultType = Result
  override type PipelineResultType = MitwittererPipelineResult[Result]

  def qualityFactorStep(
    qualityFactorStatus: QualityFactorStatus
  ): Step[Query, QualityFactorEtwitterecutorResult] =
    new Step[Query, QualityFactorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = MitwittererPipelineConfig.qualityFactorStep

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
        previousResult: MitwittererPipelineResult[Result]
      ): Query = query

      override def resultUpdater(
        previousPipelineResult: MitwittererPipelineResult[Result],
        etwitterecutorResult: QualityFactorEtwitterecutorResult
      ): MitwittererPipelineResult[Result] =
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
    override def identifier: PipelineStepIdentifier = MitwittererPipelineConfig.gatesStep

    override def etwitterecutorArrow: Arrow[Query, GateEtwitterecutorResult] =
      gateEtwitterecutor.arrow(gates, contetwittert)

    override def inputAdaptor(query: Query, previousResult: MitwittererPipelineResult[Result]): Query =
      query

    override def resultUpdater(
      previousPipelineResult: MitwittererPipelineResult[Result],
      etwitterecutorResult: GateEtwitterecutorResult
    ): MitwittererPipelineResult[Result] =
      previousPipelineResult.copy(gateResult = Some(etwitterecutorResult))
  }

  def fetchQueryFeaturesStep(
    queryFeatureHydrators: Seq[QueryFeatureHydrator[Query]],
    stepIdentifier: PipelineStepIdentifier,
    updater: ResultUpdater[MitwittererPipelineResult[Result], QueryFeatureHydratorEtwitterecutor.Result],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[Query, QueryFeatureHydratorEtwitterecutor.Result] =
    new Step[Query, QueryFeatureHydratorEtwitterecutor.Result] {
      override def identifier: PipelineStepIdentifier = stepIdentifier

      override def etwitterecutorArrow: Arrow[Query, QueryFeatureHydratorEtwitterecutor.Result] =
        queryFeatureHydratorEtwitterecutor.arrow(
          queryFeatureHydrators,
          MitwittererPipelineConfig.stepsAsyncFeatureHydrationCanBeCompletedBy,
          contetwittert)

      override def inputAdaptor(
        query: Query,
        previousResult: MitwittererPipelineResult[Result]
      ): Query = query

      override def resultUpdater(
        previousPipelineResult: MitwittererPipelineResult[Result],
        etwitterecutorResult: QueryFeatureHydratorEtwitterecutor.Result
      ): MitwittererPipelineResult[Result] =
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
        MitwittererPipelineConfig.asyncFeaturesStep(stepToHydrateFor)

      override def etwitterecutorArrow: Arrow[AsyncFeatureMap, AsyncFeatureMapEtwitterecutorResults] =
        asyncFeatureMapEtwitterecutor.arrow(
          stepToHydrateFor,
          identifier,
          contetwittert
        )

      override def inputAdaptor(
        query: Query,
        previousResult: MitwittererPipelineResult[Result]
      ): AsyncFeatureMap =
        previousResult.mergedAsyncQueryFeatures
          .getOrElse(
            throw InvalidStepStateEtwitterception(identifier, "MergedAsyncQueryFeatures")
          )

      override def resultUpdater(
        previousPipelineResult: MitwittererPipelineResult[Result],
        etwitterecutorResult: AsyncFeatureMapEtwitterecutorResults
      ): MitwittererPipelineResult[Result] = previousPipelineResult.copy(
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
      override def identifier: PipelineStepIdentifier = MitwittererPipelineConfig.candidatePipelinesStep

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
        previousResult: MitwittererPipelineResult[Result]
      ): CandidatePipeline.Inputs[Query] = CandidatePipeline.Inputs[Query](query, Seq.empty)

      override def resultUpdater(
        previousPipelineResult: MitwittererPipelineResult[Result],
        etwitterecutorResult: CandidatePipelineEtwitterecutorResult
      ): MitwittererPipelineResult[Result] =
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
        MitwittererPipelineConfig.dependentCandidatePipelinesStep

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
        previousResult: MitwittererPipelineResult[Result]
      ): CandidatePipeline.Inputs[Query] = {
        val previousCandidates = previousResult.candidatePipelineResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "Candidates")
          }.candidatePipelineResults.flatMap(_.result.getOrElse(Seq.empty))
        CandidatePipeline.Inputs[Query](query, previousCandidates)
      }

      override def resultUpdater(
        previousPipelineResult: MitwittererPipelineResult[Result],
        etwitterecutorResult: CandidatePipelineEtwitterecutorResult
      ): MitwittererPipelineResult[Result] =
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

  def resultSelectorsStep(
    selectors: Seq[Selector[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] =
    new Step[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = MitwittererPipelineConfig.resultSelectorsStep

      override def etwitterecutorArrow: Arrow[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] =
        selectorEtwitterecutor.arrow(selectors, contetwittert)

      override def inputAdaptor(
        query: Query,
        previousResult: MitwittererPipelineResult[Result]
      ): SelectorEtwitterecutor.Inputs[Query] = {
        val candidates = previousResult.candidatePipelineResults
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "Candidates")
          }.candidatePipelineResults.flatMap(_.result.getOrElse(Seq.empty))

        val dependentCandidates =
          previousResult.dependentCandidatePipelineResults
            .getOrElse {
              throw InvalidStepStateEtwitterception(identifier, "DependentCandidates")
            }.candidatePipelineResults.flatMap(_.result.getOrElse(Seq.empty))

        SelectorEtwitterecutor.Inputs(
          query = query,
          candidatesWithDetails = candidates ++ dependentCandidates
        )
      }

      override def resultUpdater(
        previousPipelineResult: MitwittererPipelineResult[Result],
        etwitterecutorResult: SelectorEtwitterecutorResult
      ): MitwittererPipelineResult[Result] =
        previousPipelineResult.copy(resultSelectorResults = Some(etwitterecutorResult))
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
      override def identifier: PipelineStepIdentifier = MitwittererPipelineConfig.domainMarshallerStep

      override def etwitterecutorArrow: Arrow[
        DomainMarshallerEtwitterecutor.Inputs[Query],
        DomainMarshallerEtwitterecutor.Result[DomainResultType]
      ] =
        domainMarshallerEtwitterecutor.arrow(domainMarshaller, contetwittert)

      override def inputAdaptor(
        query: Query,
        previousResult: MitwittererPipelineResult[Result]
      ): DomainMarshallerEtwitterecutor.Inputs[Query] = {
        val selectorResults = previousResult.resultSelectorResults.getOrElse {
          throw InvalidStepStateEtwitterception(identifier, "SelectorResults")
        }

        DomainMarshallerEtwitterecutor.Inputs(
          query = query,
          candidatesWithDetails = selectorResults.selectedCandidates
        )
      }

      override def resultUpdater(
        previousPipelineResult: MitwittererPipelineResult[Result],
        etwitterecutorResult: DomainMarshallerEtwitterecutor.Result[DomainResultType]
      ): MitwittererPipelineResult[Result] = previousPipelineResult.copy(
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
    override def identifier: PipelineStepIdentifier = MitwittererPipelineConfig.resultSideEffectsStep

    override def etwitterecutorArrow: Arrow[
      PipelineResultSideEffect.Inputs[Query, DomainResultType],
      PipelineResultSideEffectEtwitterecutor.Result
    ] = pipelineResultSideEffectEtwitterecutor.arrow(sideEffects, contetwittert)

    override def inputAdaptor(
      query: Query,
      previousResult: MitwittererPipelineResult[Result]
    ): PipelineResultSideEffect.Inputs[Query, DomainResultType] = {

      val selectorResults = previousResult.resultSelectorResults.getOrElse {
        throw InvalidStepStateEtwitterception(identifier, "SelectorResults")
      }

      val domainMarshallerResults = previousResult.domainMarshallerResults.getOrElse {
        throw InvalidStepStateEtwitterception(identifier, "DomainMarshallerResults")
      }

      PipelineResultSideEffect.Inputs[Query, DomainResultType](
        query = query,
        selectedCandidates = selectorResults.selectedCandidates,
        remainingCandidates = selectorResults.remainingCandidates,
        droppedCandidates = selectorResults.droppedCandidates,
        response = domainMarshallerResults.result.asInstanceOf[DomainResultType]
      )
    }

    override def resultUpdater(
      previousPipelineResult: MitwittererPipelineResult[Result],
      etwitterecutorResult: PipelineResultSideEffectEtwitterecutor.Result
    ): MitwittererPipelineResult[Result] =
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
    override def identifier: PipelineStepIdentifier = MitwittererPipelineConfig.transportMarshallerStep

    override def etwitterecutorArrow: Arrow[TransportMarshallerEtwitterecutor.Inputs[
      DomainResultType
    ], TransportMarshallerEtwitterecutor.Result[Result]] =
      transportMarshallerEtwitterecutor.arrow(transportMarshaller, contetwittert)

    override def inputAdaptor(
      query: Query,
      previousResult: MitwittererPipelineResult[Result]
    ): TransportMarshallerEtwitterecutor.Inputs[DomainResultType] = {
      val domainMarshallingResults = previousResult.domainMarshallerResults.getOrElse {
        throw InvalidStepStateEtwitterception(identifier, "DomainMarshallerResults")
      }

      // Since the PipelineResult just uses HasMarshalling
      val domainResult = domainMarshallingResults.result.asInstanceOf[DomainResultType]

      TransportMarshallerEtwitterecutor.Inputs(domainResult)
    }

    override def resultUpdater(
      previousPipelineResult: MitwittererPipelineResult[Result],
      etwitterecutorResult: TransportMarshallerEtwitterecutor.Result[Result]
    ): MitwittererPipelineResult[Result] = previousPipelineResult.copy(
      transportMarshallerResults = Some(etwitterecutorResult),
      result = Some(etwitterecutorResult.result)
    )
  }

  def build(
    parentComponentIdentifierStack: ComponentIdentifierStack,
    config: MitwittererPipelineConfig[Query, DomainResultType, Result]
  ): MitwittererPipeline[Query, Result] = {

    val pipelineIdentifier = config.identifier

    val contetwittert = Etwitterecutor.Contetwittert(
      PipelineFailureClassifier(
        config.failureClassifier.orElse(StoppedGateEtwitterception.classifier(ProductDisabled))),
      parentComponentIdentifierStack.push(pipelineIdentifier)
    )

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

    val builtSteps = Seq(
      qualityFactorStep(qualityFactorStatus),
      gatesStep(config.gates, contetwittert),
      fetchQueryFeaturesStep(
        config.fetchQueryFeatures,
        MitwittererPipelineConfig.fetchQueryFeaturesStep,
        (previousPipelineResult, etwitterecutorResult) =>
          previousPipelineResult.copy(queryFeatures = Some(etwitterecutorResult)),
        contetwittert
      ),
      fetchQueryFeaturesStep(
        config.fetchQueryFeaturesPhase2,
        MitwittererPipelineConfig.fetchQueryFeaturesPhase2Step,
        (previousPipelineResult, etwitterecutorResult) =>
          previousPipelineResult.copy(
            queryFeaturesPhase2 = Some(etwitterecutorResult),
            mergedAsyncQueryFeatures = Some(
              previousPipelineResult.queryFeatures
                .getOrElse(throw InvalidStepStateEtwitterception(
                  MitwittererPipelineConfig.fetchQueryFeaturesPhase2Step,
                  "QueryFeatures"))
                .asyncFeatureMap ++ etwitterecutorResult.asyncFeatureMap)
          ),
        contetwittert
      ),
      asyncFeaturesStep(MitwittererPipelineConfig.candidatePipelinesStep, contetwittert),
      candidatePipelinesStep(
        candidatePipelines,
        config.defaultFailOpenPolicy,
        config.failOpenPolicies,
        qualityFactorObserverByPipeline,
        contetwittert),
      asyncFeaturesStep(MitwittererPipelineConfig.dependentCandidatePipelinesStep, contetwittert),
      dependentCandidatePipelinesStep(
        dependentCandidatePipelines,
        config.defaultFailOpenPolicy,
        config.failOpenPolicies,
        qualityFactorObserverByPipeline,
        contetwittert),
      asyncFeaturesStep(MitwittererPipelineConfig.resultSelectorsStep, contetwittert),
      resultSelectorsStep(config.resultSelectors, contetwittert),
      domainMarshallingStep(config.domainMarshaller, contetwittert),
      asyncFeaturesStep(MitwittererPipelineConfig.resultSideEffectsStep, contetwittert),
      resultSideEffectsStep(config.resultSideEffects, contetwittert),
      transportMarshallingStep(config.transportMarshaller, contetwittert)
    )

    val finalArrow = buildCombinedArrowFromSteps(
      steps = builtSteps,
      contetwittert = contetwittert,
      initialEmptyResult = MitwittererPipelineResult.empty,
      stepsInOrderFromConfig = MitwittererPipelineConfig.stepsInOrder
    )

    val configFromBuilder = config
    new MitwittererPipeline[Query, Result] {
      override private[core] val config: MitwittererPipelineConfig[Query, _, Result] = configFromBuilder
      override val arrow: Arrow[Query, MitwittererPipelineResult[Result]] = finalArrow
      override val identifier: MitwittererPipelineIdentifier = pipelineIdentifier
      override val alerts: Seq[Alert] = config.alerts
      override val children: Seq[Component] =
        config.gates ++
          config.fetchQueryFeatures ++
          candidatePipelines ++
          dependentCandidatePipelines ++
          config.resultSideEffects ++
          Seq(config.domainMarshaller, config.transportMarshaller)
    }
  }
}
