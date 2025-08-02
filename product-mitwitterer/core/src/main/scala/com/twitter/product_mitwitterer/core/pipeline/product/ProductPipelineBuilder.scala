package com.twitter.product_mitwitterer.core.pipeline.product

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.tracing.Trace
import com.twitter.finagle.transport.Transport
import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.gate.DenyLoggedOutUsersGate
import com.twitter.product_mitwitterer.core.gate.ParamGate
import com.twitter.product_mitwitterer.core.gate.ParamGate.EnabledGateSuffitwitter
import com.twitter.product_mitwitterer.core.gate.ParamGate.SupportedClientGateSuffitwitter
import com.twitter.product_mitwitterer.core.model.common.Component
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifierStack
import com.twitter.product_mitwitterer.core.model.common.identifier.ProductPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.InvalidStepStateEtwitterception
import com.twitter.product_mitwitterer.core.pipeline.Pipeline
import com.twitter.product_mitwitterer.core.pipeline.PipelineBuilder
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.mitwitterer.MitwittererPipelineBuilderFactory
import com.twitter.product_mitwitterer.core.pipeline.mitwitterer.MitwittererPipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.mitwitterer.MitwittererPipelineResult
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureClassifier
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.ProductDisabled
import com.twitter.product_mitwitterer.core.pipeline.recommendation.RecommendationPipelineBuilderFactory
import com.twitter.product_mitwitterer.core.pipeline.recommendation.RecommendationPipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.recommendation.RecommendationPipelineResult
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorObserver
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorStatus
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.StoppedGateEtwitterception
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecution_logger.PipelineEtwitterecutionLogger
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecutor.PipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecutor.PipelineEtwitterecutorRequest
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecutor.PipelineEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.pipeline_selector_etwitterecutor.PipelineSelectorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_selector_etwitterecutor.PipelineSelectorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.quality_factor_etwitterecutor.QualityFactorEtwitterecutorResult
import com.twitter.stitch.Arrow
import com.twitter.stringcenter.client.StringCenterRequestContetwittert
import com.twitter.stringcenter.client.stitch.StringCenterRequestContetwittertLetter
import com.twitter.timelines.configapi.Params
import com.twitter.util.logging.Logging
import org.slf4j.MDC

class ProductPipelineBuilder[TRequest <: Request, Query <: PipelineQuery, Response](
  gateEtwitterecutor: GateEtwitterecutor,
  pipelineSelectorEtwitterecutor: PipelineSelectorEtwitterecutor,
  pipelineEtwitterecutor: PipelineEtwitterecutor,
  mitwittererPipelineBuilderFactory: MitwittererPipelineBuilderFactory,
  recommendationPipelineBuilderFactory: RecommendationPipelineBuilderFactory,
  override val statsReceiver: StatsReceiver,
  pipelineEtwitterecutionLogger: PipelineEtwitterecutionLogger)
    etwittertends PipelineBuilder[ProductPipelineRequest[TRequest]]
    with Logging { builder =>

  override type UnderlyingResultType = Response
  override type PipelineResultType = ProductPipelineResult[Response]

  /**
   * Query Transformer Step is implemented inline instead of using an etwitterecutor.
   *
   * It's a simple, synchronous step that etwitterecutes the query transformer.
   *
   * Since the output of the transformer is used in multiple other steps (Gate, Pipeline Etwitterecution),
   * we've promoted the transformer to a step so that it's outputs can be reused easily.
   */
  def pipelineQueryTransformerStep(
    queryTransformer: (TRequest, Params) => Query,
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[ProductPipelineRequest[TRequest], Query] =
    new Step[ProductPipelineRequest[TRequest], Query] {

      override def identifier: PipelineStepIdentifier =
        ProductPipelineConfig.pipelineQueryTransformerStep

      override def etwitterecutorArrow: Arrow[ProductPipelineRequest[TRequest], Query] = {
        wrapWithErrorHandling(contetwittert, identifier)(
          Arrow.map[ProductPipelineRequest[TRequest], Query] {
            case ProductPipelineRequest(request, params) => queryTransformer(request, params)
          }
        )
      }

      override def inputAdaptor(
        query: ProductPipelineRequest[TRequest],
        previousResult: ProductPipelineResult[Response]
      ): ProductPipelineRequest[TRequest] = query

      override def resultUpdater(
        previousPipelineResult: ProductPipelineResult[Response],
        etwitterecutorResult: Query
      ): ProductPipelineResult[Response] =
        previousPipelineResult.copy(transformedQuery = Some(etwitterecutorResult))
    }

  def qualityFactorStep(
    qualityFactorStatus: QualityFactorStatus
  ): Step[Query, QualityFactorEtwitterecutorResult] = {
    new Step[Query, QualityFactorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = ProductPipelineConfig.qualityFactorStep

      override def etwitterecutorArrow: Arrow[Query, QualityFactorEtwitterecutorResult] =
        Arrow
          .map[Query, QualityFactorEtwitterecutorResult] { _ =>
            QualityFactorEtwitterecutorResult(
              pipelineQualityFactors =
                qualityFactorStatus.qualityFactorByPipeline.mapValues(_.currentValue)
            )
          }

      override def inputAdaptor(
        query: ProductPipelineRequest[TRequest],
        previousResult: ProductPipelineResult[Response]
      ): Query = previousResult.transformedQuery
        .getOrElse {
          throw InvalidStepStateEtwitterception(identifier, "TransformedQuery")
        }.asInstanceOf[Query]

      override def resultUpdater(
        previousPipelineResult: ProductPipelineResult[Response],
        etwitterecutorResult: QualityFactorEtwitterecutorResult
      ): ProductPipelineResult[Response] = {
        previousPipelineResult.copy(
          transformedQuery = previousPipelineResult.transformedQuery.map {
            case queryWithQualityFactor: HasQualityFactorStatus =>
              queryWithQualityFactor
                .withQualityFactorStatus(qualityFactorStatus).asInstanceOf[Query]
            case query =>
              query
          },
          qualityFactorResult = Some(etwitterecutorResult)
        )
      }
    }
  }

  def gatesStep(
    gates: Seq[Gate[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[Query, GateEtwitterecutorResult] = new Step[Query, GateEtwitterecutorResult] {
    override def identifier: PipelineStepIdentifier = ProductPipelineConfig.gatesStep

    override def etwitterecutorArrow: Arrow[Query, GateEtwitterecutorResult] = {
      gateEtwitterecutor.arrow(gates, contetwittert)
    }

    override def inputAdaptor(
      query: ProductPipelineRequest[TRequest],
      previousResult: ProductPipelineResult[Response]
    ): Query = previousResult.transformedQuery
      .getOrElse {
        throw InvalidStepStateEtwitterception(identifier, "TransformedQuery")
      }.asInstanceOf[Query]

    override def resultUpdater(
      previousPipelineResult: ProductPipelineResult[Response],
      etwitterecutorResult: GateEtwitterecutorResult
    ): ProductPipelineResult[Response] =
      previousPipelineResult.copy(gateResult = Some(etwitterecutorResult))
  }

  def pipelineSelectorStep(
    pipelineByIdentifer: Map[ComponentIdentifier, Pipeline[Query, Response]],
    pipelineSelector: Query => ComponentIdentifier,
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[Query, PipelineSelectorEtwitterecutorResult] =
    new Step[Query, PipelineSelectorEtwitterecutorResult] {
      override def identifier: PipelineStepIdentifier = ProductPipelineConfig.pipelineSelectorStep

      override def etwitterecutorArrow: Arrow[
        Query,
        PipelineSelectorEtwitterecutorResult
      ] = pipelineSelectorEtwitterecutor.arrow(pipelineByIdentifer, pipelineSelector, contetwittert)

      override def inputAdaptor(
        query: ProductPipelineRequest[TRequest],
        previousResult: ProductPipelineResult[Response]
      ): Query =
        previousResult.transformedQuery
          .getOrElse(throw InvalidStepStateEtwitterception(identifier, "TransformedQuery")).asInstanceOf[
            Query]

      override def resultUpdater(
        previousPipelineResult: ProductPipelineResult[Response],
        etwitterecutorResult: PipelineSelectorEtwitterecutorResult
      ): ProductPipelineResult[Response] =
        previousPipelineResult.copy(pipelineSelectorResult = Some(etwitterecutorResult))
    }

  def pipelineEtwitterecutionStep(
    pipelineByIdentifier: Map[ComponentIdentifier, Pipeline[Query, Response]],
    qualityFactorObserverByPipeline: Map[ComponentIdentifier, QualityFactorObserver],
    contetwittert: Etwitterecutor.Contetwittert
  ): Step[PipelineEtwitterecutorRequest[Query], PipelineEtwitterecutorResult[Response]] =
    new Step[PipelineEtwitterecutorRequest[Query], PipelineEtwitterecutorResult[Response]] {
      override def identifier: PipelineStepIdentifier = ProductPipelineConfig.pipelineEtwitterecutionStep

      override def etwitterecutorArrow: Arrow[
        PipelineEtwitterecutorRequest[Query],
        PipelineEtwitterecutorResult[Response]
      ] = {
        pipelineEtwitterecutor.arrow(pipelineByIdentifier, qualityFactorObserverByPipeline, contetwittert)
      }

      override def inputAdaptor(
        request: ProductPipelineRequest[TRequest],
        previousResult: ProductPipelineResult[Response]
      ): PipelineEtwitterecutorRequest[Query] = {
        val query = previousResult.transformedQuery
          .getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "TransformedQuery")
          }.asInstanceOf[Query]

        val pipelineIdentifier = previousResult.pipelineSelectorResult
          .map(_.pipelineIdentifier).getOrElse {
            throw InvalidStepStateEtwitterception(identifier, "PipelineSelectorResult")
          }

        PipelineEtwitterecutorRequest(query, pipelineIdentifier)
      }

      override def resultUpdater(
        previousPipelineResult: ProductPipelineResult[Response],
        etwitterecutorResult: PipelineEtwitterecutorResult[Response]
      ): ProductPipelineResult[Response] = {

        val mitwittererPipelineResult = etwitterecutorResult.pipelineResult match {
          case mitwittererPipelineResult: MitwittererPipelineResult[Response] @unchecked =>
            Some(mitwittererPipelineResult)
          case _ =>
            None
        }

        val recommendationPipelineResult = etwitterecutorResult.pipelineResult match {
          case recommendationPipelineResult: RecommendationPipelineResult[
                _,
                Response
              ] @unchecked =>
            Some(recommendationPipelineResult)
          case _ =>
            None
        }

        previousPipelineResult.copy(
          mitwittererPipelineResult = mitwittererPipelineResult,
          recommendationPipelineResult = recommendationPipelineResult,
          traceId = Trace.idOption.map(_.traceId.toString()),
          result = etwitterecutorResult.pipelineResult.result
        )
      }
    }

  def build(
    parentComponentIdentifierStack: ComponentIdentifierStack,
    config: ProductPipelineConfig[TRequest, Query, Response]
  ): ProductPipeline[TRequest, Response] = {

    val pipelineIdentifier = config.identifier

    val contetwittert = Etwitterecutor.Contetwittert(
      PipelineFailureClassifier(
        config.failureClassifier.orElse(StoppedGateEtwitterception.classifier(ProductDisabled))),
      parentComponentIdentifierStack.push(pipelineIdentifier)
    )

    val denyLoggedOutUsersGate = if (config.denyLoggedOutUsers) {
      Some(DenyLoggedOutUsersGate(pipelineIdentifier))
    } else {
      None
    }
    val enabledGate: ParamGate =
      ParamGate(pipelineIdentifier + EnabledGateSuffitwitter, config.paramConfig.EnabledDeciderParam)
    val supportedClientGate =
      ParamGate(
        pipelineIdentifier + SupportedClientGateSuffitwitter,
        config.paramConfig.SupportedClientParam)

    /**
     * Evaluate enabled decider gate first since if it's off, there is no reason to proceed
     * Netwittert evaluate supported client feature switch gate, followed by customer configured gates
     */
    val allGates =
      denyLoggedOutUsersGate.toSeq ++: enabledGate +: supportedClientGate +: config.gates

    val childPipelines: Seq[Pipeline[Query, Response]] =
      config.pipelines.map {
        case mitwittererConfig: MitwittererPipelineConfig[Query, _, Response] =>
          mitwittererConfig.build(contetwittert.componentStack, mitwittererPipelineBuilderFactory)
        case recommendationConfig: RecommendationPipelineConfig[Query, _, _, Response] =>
          recommendationConfig.build(contetwittert.componentStack, recommendationPipelineBuilderFactory)
        case other =>
          throw new IllegalArgumentEtwitterception(
            s"Product Pipelines only support Mitwitterer and Recommendation pipelines, not $other")
      }

    val pipelineByIdentifier: Map[ComponentIdentifier, Pipeline[Query, Response]] =
      childPipelines.map { pipeline =>
        (pipeline.identifier, pipeline)
      }.toMap

    val qualityFactorStatus: QualityFactorStatus =
      QualityFactorStatus.build(config.qualityFactorConfigs)

    val qualityFactorObserverByPipeline = qualityFactorStatus.qualityFactorByPipeline.mapValues {
      qualityFactor =>
        qualityFactor.buildObserver()
    }

    buildGaugesForQualityFactor(pipelineIdentifier, qualityFactorStatus, statsReceiver)

    /**
     * Initialize MDC with access logging with everything we have at request time. We can put
     * more stuff into MDC later down the pipeline, but at risk of etwitterceptions/errors preventing
     * them from being added
     */
    val mdcInitArrow =
      Arrow.map[ProductPipelineRequest[TRequest], ProductPipelineRequest[TRequest]] { request =>
        val serviceIdentifier = ServiceIdentifier.fromCertificate(Transport.peerCertificate)
        MDC.put("product", config.product.identifier.name)
        MDC.put("serviceIdentifier", ServiceIdentifier.asString(serviceIdentifier))
        request
      }

    val builtSteps = Seq(
      pipelineQueryTransformerStep(config.pipelineQueryTransformer, contetwittert),
      qualityFactorStep(qualityFactorStatus),
      gatesStep(allGates, contetwittert),
      pipelineSelectorStep(pipelineByIdentifier, config.pipelineSelector, contetwittert),
      pipelineEtwitterecutionStep(pipelineByIdentifier, qualityFactorObserverByPipeline, contetwittert)
    )

    val underlying: Arrow[ProductPipelineRequest[TRequest], ProductPipelineResult[Response]] =
      buildCombinedArrowFromSteps(
        steps = builtSteps,
        contetwittert = contetwittert,
        initialEmptyResult = ProductPipelineResult.empty,
        stepsInOrderFromConfig = ProductPipelineConfig.stepsInOrder
      )

    /**
     * Unlike other components and pipelines, [[ProductPipeline]] must be observed in the
     * [[ProductPipelineBuilder]] directly because the resulting [[ProductPipeline.arrow]]
     * is run directly without an etwitterecutor so must contain all stats.
     */
    val observed =
      wrapProductPipelineWithEtwitterecutorBookkeeping[
        ProductPipelineRequest[TRequest],
        ProductPipelineResult[Response]
      ](contetwittert, pipelineIdentifier)(underlying)

    val finalArrow: Arrow[ProductPipelineRequest[TRequest], ProductPipelineResult[Response]] =
      Arrow
        .letWithArg[
          ProductPipelineRequest[TRequest],
          ProductPipelineResult[Response],
          StringCenterRequestContetwittert](StringCenterRequestContetwittertLetter)(request =>
          StringCenterRequestContetwittert(
            request.request.clientContetwittert.languageCode,
            request.request.clientContetwittert.countryCode
          ))(
          mdcInitArrow
            .andThen(observed)
            .onSuccess(result => result.transformedQuery.map(pipelineEtwitterecutionLogger(_, result))))

    val configFromBuilder = config
    new ProductPipeline[TRequest, Response] {
      override private[core] val config: ProductPipelineConfig[TRequest, _, Response] =
        configFromBuilder
      override val arrow: Arrow[ProductPipelineRequest[TRequest], ProductPipelineResult[Response]] =
        finalArrow
      override val identifier: ProductPipelineIdentifier = pipelineIdentifier
      override val alerts: Seq[Alert] = config.alerts
      override val debugAccessPolicies: Set[AccessPolicy] = config.debugAccessPolicies
      override val children: Seq[Component] = allGates ++ childPipelines
    }
  }
}
