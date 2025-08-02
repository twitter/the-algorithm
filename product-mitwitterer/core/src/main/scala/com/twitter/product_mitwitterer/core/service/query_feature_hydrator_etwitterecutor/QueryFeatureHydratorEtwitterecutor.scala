package com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor

import com.fastertwitterml.jackson.databind.annotation.JsonSerialize
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.AsyncHydrator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.featurestorev1.FeatureStoreV1QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.Conditionally
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.Etwitterecutor._
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.feature_hydrator_observer.FeatureHydratorObserver
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor.AsyncIndividualFeatureHydratorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor.BaseIndividualFeatureHydratorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor.FeatureHydratorDisabled
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor.IndividualFeatureHydratorResult
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor.validateAsyncQueryFeatureHydrator
import com.twitter.stitch.Arrow
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class QueryFeatureHydratorEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {

  def arrow[Query <: PipelineQuery](
    hydrators: Seq[BaseQueryFeatureHydrator[Query, _]],
    validPipelineSteps: Set[PipelineStepIdentifier],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Query, QueryFeatureHydratorEtwitterecutor.Result] = {

    val observer = new FeatureHydratorObserver(statsReceiver, hydrators, contetwittert)
    val hydratorsWithErrorHandling =
      hydrators.map { hydrator =>
        val queryFeatureHydratorArrow =
          getQueryHydratorArrow(hydrator, contetwittert, observer)
        val wrappedWithAsyncHandling =
          handleAsyncHydrator(hydrator, validPipelineSteps, queryFeatureHydratorArrow)
        handleConditionally(hydrator, wrappedWithAsyncHandling)
      }

    Arrow
      .collect(hydratorsWithErrorHandling)
      .map {
        results: Seq[
          (FeatureHydratorIdentifier, BaseIndividualFeatureHydratorResult)
        ] =>
          val combinedFeatureMap = FeatureMap.merge(results.collect {
            case (_, IndividualFeatureHydratorResult(featureMap)) => featureMap
          })

          val asyncFeatureMaps = results.collect {
            case (
                  hydratorIdentifier,
                  AsyncIndividualFeatureHydratorResult(hydrateBefore, featuresToHydrate, ref)) =>
              (hydratorIdentifier, hydrateBefore, featuresToHydrate, ref)
          }

          QueryFeatureHydratorEtwitterecutor.Result(
            individualFeatureMaps = results.toMap,
            featureMap = combinedFeatureMap,
            asyncFeatureMap = AsyncFeatureMap.fromFeatureMaps(asyncFeatureMaps)
          )
      }
  }

  def handleConditionally[Query <: PipelineQuery](
    hydrator: BaseQueryFeatureHydrator[Query, _],
    arrow: Arrow[
      Query,
      BaseIndividualFeatureHydratorResult
    ]
  ): Arrow[
    Query,
    (FeatureHydratorIdentifier, BaseIndividualFeatureHydratorResult)
  ] = {
    val conditionallyRunArrow = hydrator match {
      case hydrator: BaseQueryFeatureHydrator[Query, _] with Conditionally[Query @unchecked] =>
        Arrow.ifelse[Query, BaseIndividualFeatureHydratorResult](
          hydrator.onlyIf,
          arrow,
          Arrow.value(FeatureHydratorDisabled)
        )
      case _ => arrow
    }

    Arrow.join(
      Arrow.value(hydrator.identifier),
      conditionallyRunArrow
    )
  }

  def handleAsyncHydrator[Query <: PipelineQuery](
    hydrator: BaseQueryFeatureHydrator[Query, _],
    validPipelineSteps: Set[PipelineStepIdentifier],
    arrow: Arrow[
      Query,
      IndividualFeatureHydratorResult
    ]
  ): Arrow[Query, BaseIndividualFeatureHydratorResult] = {
    hydrator match {
      case hydrator: BaseQueryFeatureHydrator[
            Query,
            _
          ] with AsyncHydrator =>
        validateAsyncQueryFeatureHydrator(hydrator, validPipelineSteps)

        startArrowAsync(arrow.map(_.featureMap))
          .map { ref =>
            AsyncIndividualFeatureHydratorResult(
              hydrator.hydrateBefore,
              hydrator.features.asInstanceOf[Set[Feature[_, _]]],
              ref
            )
          }

      case _ => arrow
    }
  }

  def getQueryHydratorArrow[Query <: PipelineQuery](
    hydrator: BaseQueryFeatureHydrator[Query, _],
    contetwittert: Etwitterecutor.Contetwittert,
    queryFeatureHydratorObserver: FeatureHydratorObserver
  ): Arrow[Query, IndividualFeatureHydratorResult] = {

    val componentEtwitterecutorContetwittert = contetwittert.pushToComponentStack(hydrator.identifier)
    val hydratorArrow: Arrow[Query, FeatureMap] =
      Arrow.flatMap { query: Query => hydrator.hydrate(query) }

    val validationFn: FeatureMap => FeatureMap = hydrator match {
      // Feature store query hydrators store the resulting PredictionRecord and not
      // the features, so we cannot validate the same way
      case _: FeatureStoreV1QueryFeatureHydrator[Query] =>
        identity
      case _ =>
        validateFeatureMap(
          hydrator.features.asInstanceOf[Set[Feature[_, _]]],
          _,
          componentEtwitterecutorContetwittert)
    }

    // record the component-level stats
    val observedArrow =
      wrapComponentWithEtwitterecutorBookkeeping[Query, FeatureMap](
        contetwittert,
        hydrator.identifier
      )(hydratorArrow.map(validationFn))

    // store non-configuration errors in the FeatureMap
    val liftNonValidationFailuresToFailedFeatures = Arrow.handle[FeatureMap, FeatureMap] {
      case NotAMisconfiguredFeatureMapFailure(e) =>
        featureMapWithFailuresForFeatures(
          hydrator.features.asInstanceOf[Set[Feature[_, _]]],
          e,
          componentEtwitterecutorContetwittert)
    }

    val observedLiftedAndWrapped = observedArrow
      .andThen(liftNonValidationFailuresToFailedFeatures)
      .applyEffect(Arrow.map[FeatureMap, Unit](featureMap =>
        // record per-feature stats, this is separate from the component stats handled by `wrapWithEtwitterecutorBookkeeping`
        queryFeatureHydratorObserver.observeFeatureSuccessAndFailures(hydrator, Seq(featureMap))))
      .map(IndividualFeatureHydratorResult)

    observedLiftedAndWrapped
  }
}

object QueryFeatureHydratorEtwitterecutor {
  case class Result(
    individualFeatureMaps: Map[
      FeatureHydratorIdentifier,
      BaseIndividualFeatureHydratorResult
    ],
    featureMap: FeatureMap,
    asyncFeatureMap: AsyncFeatureMap)
      etwittertends EtwitterecutorResult

  sealed trait BaseIndividualFeatureHydratorResult

  case object FeatureHydratorDisabled etwittertends BaseIndividualFeatureHydratorResult
  case class IndividualFeatureHydratorResult(featureMap: FeatureMap)
      etwittertends BaseIndividualFeatureHydratorResult

  /** Async result, serializes without the [[Stitch]] field since it's not serializable */
  @JsonSerialize(using = classOf[AsyncIndividualFeatureHydratorResultSerializer])
  case class AsyncIndividualFeatureHydratorResult(
    hydrateBefore: PipelineStepIdentifier,
    features: Set[Feature[_, _]],
    ref: Stitch[FeatureMap])
      etwittertends BaseIndividualFeatureHydratorResult

  /**
   * Validates whether the [[AsyncHydrator.hydrateBefore]] [[PipelineStepIdentifier]] is valid
   *
   * @param asyncQueryFeatureHydrator the hydrator to validate
   * @param validPipelineSteps        a Set of [[PipelineStepIdentifier]]s which are valid places to populate async
   *                                  [[Feature]]s in a [[com.twitter.product_mitwitterer.core.pipeline.Pipeline]]
   */
  def validateAsyncQueryFeatureHydrator(
    asyncQueryFeatureHydrator: AsyncHydrator,
    validPipelineSteps: Set[PipelineStepIdentifier]
  ): Unit =
    require(
      validPipelineSteps.contains(asyncQueryFeatureHydrator.hydrateBefore),
      s"`AsyncHydrator.hydrateBefore` contained ${asyncQueryFeatureHydrator.hydrateBefore} which was not in the parent pipeline's " +
        s"`PipelineConfig` Companion object field `stepsAsyncFeatureHydrationCanBeCompletedBy = $validPipelineSteps`."
    )
}
