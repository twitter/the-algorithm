package com.X.home_mixer.product.following

import com.X.conversions.DurationOps._
import com.X.home_mixer.marshaller.timelines.ChronologicalCursorUnmarshaller
import com.X.home_mixer.model.request.FollowingProduct
import com.X.home_mixer.model.request.FollowingProductContext
import com.X.home_mixer.model.request.HomeMixerRequest
import com.X.home_mixer.product.following.model.FollowingQuery
import com.X.home_mixer.product.following.param.FollowingParam.ServerMaxResultsParam
import com.X.home_mixer.product.following.param.FollowingParamConfig
import com.X.home_mixer.service.HomeMixerAccessPolicy.DefaultHomeMixerAccessPolicy
import com.X.home_mixer.service.HomeMixerAlertConfig.DefaultNotificationGroup
import com.X.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.X.product_mixer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.X.product_mixer.core.functional_component.common.access_policy.AccessPolicy
import com.X.product_mixer.core.functional_component.common.alert.Alert
import com.X.product_mixer.core.functional_component.common.alert.EmptyResponseRateAlert
import com.X.product_mixer.core.functional_component.common.alert.LatencyAlert
import com.X.product_mixer.core.functional_component.common.alert.P99
import com.X.product_mixer.core.functional_component.common.alert.SuccessRateAlert
import com.X.product_mixer.core.functional_component.common.alert.ThroughputAlert
import com.X.product_mixer.core.functional_component.common.alert.predicate.TriggerIfAbove
import com.X.product_mixer.core.functional_component.common.alert.predicate.TriggerIfBelow
import com.X.product_mixer.core.functional_component.common.alert.predicate.TriggerIfLatencyAbove
import com.X.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.X.product_mixer.core.model.common.identifier.ProductPipelineIdentifier
import com.X.product_mixer.core.model.marshalling.request.Product
import com.X.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.X.product_mixer.core.pipeline.PipelineConfig
import com.X.product_mixer.core.pipeline.pipeline_failure.BadRequest
import com.X.product_mixer.core.pipeline.pipeline_failure.MalformedCursor
import com.X.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.X.product_mixer.core.pipeline.product.ProductPipelineConfig
import com.X.product_mixer.core.product.ProductParamConfig
import com.X.product_mixer.core.util.SortIndexBuilder
import com.X.timelines.configapi.Params
import com.X.timelines.render.{thriftscala => urt}
import com.X.timelines.util.RequestCursorSerializer
import com.X.util.Time
import com.X.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingProductPipelineConfig @Inject() (
  followingMixerPipelineConfig: FollowingMixerPipelineConfig,
  followingParamConfig: FollowingParamConfig)
    extends ProductPipelineConfig[HomeMixerRequest, FollowingQuery, urt.TimelineResponse] {

  override val identifier: ProductPipelineIdentifier = ProductPipelineIdentifier("Following")

  override val product: Product = FollowingProduct
  override val paramConfig: ProductParamConfig = followingParamConfig

  override def pipelineQueryTransformer(
    request: HomeMixerRequest,
    params: Params
  ): FollowingQuery = {
    val context = request.productContext match {
      case Some(context: FollowingProductContext) => context
      case _ => throw PipelineFailure(BadRequest, "FollowingProductContext not found")
    }

    val debugOptions = request.debugParams.flatMap(_.debugOptions)

    /**
     * Unlike other clients, newly created tweets on Android have the sort index set to the current
     * time instead of the top sort index + 1, so these tweets get stuck at the top of the timeline
     * if subsequent timeline responses use the sort index from the previous response instead of
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
            topCursor.copy(initialSortIndex = SortIndexBuilder.timeToId(queryTime))
          case cursor => cursor
        }
    }

    FollowingQuery(
      params = params,
      clientContext = request.clientContext,
      features = None,
      pipelineCursor = pipelineCursor,
      requestedMaxResults = Some(params(ServerMaxResultsParam)),
      debugOptions = debugOptions,
      deviceContext = context.deviceContext,
      seenTweetIds = context.seenTweetIds,
      dspClientContext = context.dspClientContext
    )
  }

  override val pipelines: Seq[PipelineConfig] = Seq(followingMixerPipelineConfig)

  override def pipelineSelector(
    query: FollowingQuery
  ): ComponentIdentifier = followingMixerPipelineConfig.identifier

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

  override val debugAccessPolicies: Set[AccessPolicy] = DefaultHomeMixerAccessPolicy
}
