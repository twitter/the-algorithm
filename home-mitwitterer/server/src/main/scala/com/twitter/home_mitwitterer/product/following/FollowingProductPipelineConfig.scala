package com.twitter.home_mitwitterer.product.following

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.marshaller.timelines.ChronologicalCursorUnmarshaller
import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.home_mitwitterer.model.request.FollowingProductContetwittert
import com.twitter.home_mitwitterer.model.request.HomeMitwittererRequest
import com.twitter.home_mitwitterer.product.following.model.FollowingQuery
import com.twitter.home_mitwitterer.product.following.param.FollowingParam.ServerMatwitterResultsParam
import com.twitter.home_mitwitterer.product.following.param.FollowingParamConfig
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
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.GapCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.TopCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.MalformedCursor
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
class FollowingProductPipelineConfig @Inject() (
  followingMitwittererPipelineConfig: FollowingMitwittererPipelineConfig,
  followingParamConfig: FollowingParamConfig)
    etwittertends ProductPipelineConfig[HomeMitwittererRequest, FollowingQuery, urt.TimelineResponse] {

  override val identifier: ProductPipelineIdentifier = ProductPipelineIdentifier("Following")

  override val product: Product = FollowingProduct
  override val paramConfig: ProductParamConfig = followingParamConfig

  override def pipelineQueryTransformer(
    request: HomeMitwittererRequest,
    params: Params
  ): FollowingQuery = {
    val contetwittert = request.productContetwittert match {
      case Some(contetwittert: FollowingProductContetwittert) => contetwittert
      case _ => throw PipelineFailure(BadRequest, "FollowingProductContetwittert not found")
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
          case UrtOrderedCursor(_, id, Some(GapCursor), gapBoundaryId)
              if id.isEmpty || gapBoundaryId.isEmpty =>
            throw PipelineFailure(MalformedCursor, "Gap Cursor bounds not defined")
          case topCursor @ UrtOrderedCursor(_, _, Some(TopCursor), _) =>
            val queryTime = debugOptions.flatMap(_.requestTimeOverride).getOrElse(Time.now)
            topCursor.copy(initialSortIndetwitter = SortIndetwitterBuilder.timeToId(queryTime))
          case cursor => cursor
        }
    }

    FollowingQuery(
      params = params,
      clientContetwittert = request.clientContetwittert,
      features = None,
      pipelineCursor = pipelineCursor,
      requestedMatwitterResults = Some(params(ServerMatwitterResultsParam)),
      debugOptions = debugOptions,
      deviceContetwittert = contetwittert.deviceContetwittert,
      seenTweetIds = contetwittert.seenTweetIds,
      dspClientContetwittert = contetwittert.dspClientContetwittert
    )
  }

  override val pipelines: Seq[PipelineConfig] = Seq(followingMitwittererPipelineConfig)

  override def pipelineSelector(
    query: FollowingQuery
  ): ComponentIdentifier = followingMitwittererPipelineConfig.identifier

  override val alerts: Seq[Alert] = Seq(
    SuccessRateAlert(
      notificationGroup = DefaultNotificationGroup,
      warnPredicate = TriggerIfBelow(99.9, 20, 30),
      criticalPredicate = TriggerIfBelow(99.9, 30, 30),
    ),
    LatencyAlert(
      notificationGroup = DefaultNotificationGroup,
      percentile = P99,
      warnPredicate = TriggerIfLatencyAbove(1100.millis, 15, 30),
      criticalPredicate = TriggerIfLatencyAbove(1200.millis, 15, 30)
    ),
    ThroughputAlert(
      notificationGroup = DefaultNotificationGroup,
      warnPredicate = TriggerIfAbove(18000),
      criticalPredicate = TriggerIfAbove(20000)
    ),
    EmptyResponseRateAlert(
      notificationGroup = DefaultNotificationGroup,
      warnPredicate = TriggerIfAbove(65),
      criticalPredicate = TriggerIfAbove(80)
    )
  )

  override val debugAccessPolicies: Set[AccessPolicy] = DefaultHomeMitwittererAccessPolicy
}
