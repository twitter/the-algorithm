package com.twitter.home_mitwitterer.product.for_you

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.marshaller.timelines.ChronologicalCursorUnmarshaller
import com.twitter.home_mitwitterer.model.request.ForYouProduct
import com.twitter.home_mitwitterer.model.request.ForYouProductContetwittert
import com.twitter.home_mitwitterer.model.request.HomeMitwittererRequest
import com.twitter.home_mitwitterer.product.for_you.model.ForYouQuery
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.EnablePushToHomeMitwittererPipelineParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.EnableScoredTweetsMitwittererPipelineParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParam.ServerMatwitterResultsParam
import com.twitter.home_mitwitterer.product.for_you.param.ForYouParamConfig
import com.twitter.home_mitwitterer.service.HomeMitwittererAccessPolicy.DefaultHomeMitwittererAccessPolicy
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig.DefaultNotificationGroup
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.common.alert.EmptyResponseRateAlert
import com.twitter.product_mitwitterer.core.functional_component.common.alert.LatencyAlert
import com.twitter.product_mitwitterer.core.functional_component.common.alert.P99
import com.twitter.product_mitwitterer.core.functional_component.common.alert.SuccessRateAlert
import com.twitter.product_mitwitterer.core.functional_component.common.alert.ThroughputAlert
import com.twitter.product_mitwitterer.core.functional_component.common.alert.predicate.TriggerIfAbove
import com.twitter.product_mitwitterer.core.functional_component.common.alert.predicate.TriggerIfBelow
import com.twitter.product_mitwitterer.core.functional_component.common.alert.predicate.TriggerIfLatencyAbove
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ProductPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.TopCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineConfig
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.product_mitwitterer.core.util.SortIndetwitterBuilder
import com.twitter.timelines.configapi.Params
import com.twitter.timelines.render.{thriftscala => urt}
import com.twitter.timelines.util.RequestCursorSerializer
import com.twitter.util.Time
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ForYouProductPipelineConfig @Inject() (
  forYouTimelineScorerMitwittererPipelineConfig: ForYouTimelineScorerMitwittererPipelineConfig,
  forYouScoredTweetsMitwittererPipelineConfig: ForYouScoredTweetsMitwittererPipelineConfig,
  forYouPushToHomeMitwittererPipelineConfig: ForYouPushToHomeMitwittererPipelineConfig,
  forYouParamConfig: ForYouParamConfig)
    etwittertends ProductPipelineConfig[HomeMitwittererRequest, ForYouQuery, urt.TimelineResponse] {

  override val identifier: ProductPipelineIdentifier = ProductPipelineIdentifier("ForYou")

  override val product: Product = ForYouProduct

  override val paramConfig: ProductParamConfig = forYouParamConfig

  override def pipelineQueryTransformer(
    request: HomeMitwittererRequest,
    params: Params
  ): ForYouQuery = {
    val contetwittert = request.productContetwittert match {
      case Some(contetwittert: ForYouProductContetwittert) => contetwittert
      case _ => throw PipelineFailure(BadRequest, "ForYouProductContetwittert not found")
    }

    val debugOptions = request.debugParams.flatMap(_.debugOptions)

    /**
     * Unlike other clients, newly created tweets on Android have the sort indetwitter set to the current
     * time instead of the top sort indetwitter + 1, so these tweets get stuck at the top of the timeline
     * if subsequent timeline responses use the sort indetwitter from the previous response instead of
     * the current time.
     */
    val pipelineCursor = request.serializedRequestCursor.flatMap { cursor =>
      Try(UrtCursorSerializer.deserializeOrderedCursor(cursor))
        .getOrElse(ChronologicalCursorUnmarshaller(RequestCursorSerializer.deserialize(cursor)))
        .map {
          case topCursor @ UrtOrderedCursor(_, _, Some(TopCursor), _) =>
            val queryTime = debugOptions.flatMap(_.requestTimeOverride).getOrElse(Time.now)
            topCursor.copy(initialSortIndetwitter = SortIndetwitterBuilder.timeToId(queryTime))
          case cursor => cursor
        }
    }

    ForYouQuery(
      params = params,
      clientContetwittert = request.clientContetwittert,
      features = None,
      pipelineCursor = pipelineCursor,
      requestedMatwitterResults = Some(params(ServerMatwitterResultsParam)),
      debugOptions = debugOptions,
      deviceContetwittert = contetwittert.deviceContetwittert,
      seenTweetIds = contetwittert.seenTweetIds,
      dspClientContetwittert = contetwittert.dspClientContetwittert,
      pushToHomeTweetId = contetwittert.pushToHomeTweetId
    )
  }

  override val pipelines: Seq[PipelineConfig] = Seq(
    forYouTimelineScorerMitwittererPipelineConfig,
    forYouScoredTweetsMitwittererPipelineConfig,
    forYouPushToHomeMitwittererPipelineConfig
  )

  override def pipelineSelector(
    query: ForYouQuery
  ): ComponentIdentifier = {
    if (query.pushToHomeTweetId.isDefined && query.params(EnablePushToHomeMitwittererPipelineParam))
      forYouPushToHomeMitwittererPipelineConfig.identifier
    else if (query.params(EnableScoredTweetsMitwittererPipelineParam))
      forYouScoredTweetsMitwittererPipelineConfig.identifier
    else forYouTimelineScorerMitwittererPipelineConfig.identifier
  }

  override val alerts: Seq[Alert] = Seq(
    SuccessRateAlert(
      notificationGroup = DefaultNotificationGroup,
      warnPredicate = TriggerIfBelow(99.9, 20, 30),
      criticalPredicate = TriggerIfBelow(99.9, 30, 30),
    ),
    LatencyAlert(
      notificationGroup = DefaultNotificationGroup,
      percentile = P99,
      warnPredicate = TriggerIfLatencyAbove(2300.millis, 15, 30),
      criticalPredicate = TriggerIfLatencyAbove(2800.millis, 15, 30)
    ),
    ThroughputAlert(
      notificationGroup = DefaultNotificationGroup,
      warnPredicate = TriggerIfAbove(70000),
      criticalPredicate = TriggerIfAbove(80000)
    ),
    EmptyResponseRateAlert(
      notificationGroup = DefaultNotificationGroup,
      warnPredicate = TriggerIfAbove(2),
      criticalPredicate = TriggerIfAbove(3)
    )
  )

  override val debugAccessPolicies: Set[AccessPolicy] = DefaultHomeMitwittererAccessPolicy
}
