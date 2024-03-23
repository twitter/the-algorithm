package com.ExTwitter.home_mixer.product.for_you

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.home_mixer.marshaller.timelines.ChronologicalCursorUnmarshaller
import com.ExTwitter.home_mixer.model.request.ForYouProduct
import com.ExTwitter.home_mixer.model.request.ForYouProductContext
import com.ExTwitter.home_mixer.model.request.HomeMixerRequest
import com.ExTwitter.home_mixer.product.for_you.model.ForYouQuery
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.EnablePushToHomeMixerPipelineParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.EnableScoredTweetsMixerPipelineParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParam.ServerMaxResultsParam
import com.ExTwitter.home_mixer.product.for_you.param.ForYouParamConfig
import com.ExTwitter.home_mixer.service.HomeMixerAccessPolicy.DefaultHomeMixerAccessPolicy
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig.DefaultNotificationGroup
import com.ExTwitter.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.ExTwitter.product_mixer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.ExTwitter.product_mixer.core.functional_component.common.access_policy.AccessPolicy
import com.ExTwitter.product_mixer.core.functional_component.common.alert.Alert
import com.ExTwitter.product_mixer.core.functional_component.common.alert.EmptyResponseRateAlert
import com.ExTwitter.product_mixer.core.functional_component.common.alert.LatencyAlert
import com.ExTwitter.product_mixer.core.functional_component.common.alert.P99
import com.ExTwitter.product_mixer.core.functional_component.common.alert.SuccessRateAlert
import com.ExTwitter.product_mixer.core.functional_component.common.alert.ThroughputAlert
import com.ExTwitter.product_mixer.core.functional_component.common.alert.predicate.TriggerIfAbove
import com.ExTwitter.product_mixer.core.functional_component.common.alert.predicate.TriggerIfBelow
import com.ExTwitter.product_mixer.core.functional_component.common.alert.predicate.TriggerIfLatencyAbove
import com.ExTwitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.ExTwitter.product_mixer.core.model.common.identifier.ProductPipelineIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.Product
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.ExTwitter.product_mixer.core.pipeline.PipelineConfig
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.BadRequest
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.ExTwitter.product_mixer.core.pipeline.product.ProductPipelineConfig
import com.ExTwitter.product_mixer.core.product.ProductParamConfig
import com.ExTwitter.product_mixer.core.util.SortIndexBuilder
import com.ExTwitter.timelines.configapi.Params
import com.ExTwitter.timelines.render.{thriftscala => urt}
import com.ExTwitter.timelines.util.RequestCursorSerializer
import com.ExTwitter.util.Time
import com.ExTwitter.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouProductPipelineConfig @Inject() (
  forYouTimelineScorerMixerPipelineConfig: ForYouTimelineScorerMixerPipelineConfig,
  forYouScoredTweetsMixerPipelineConfig: ForYouScoredTweetsMixerPipelineConfig,
  forYouPushToHomeMixerPipelineConfig: ForYouPushToHomeMixerPipelineConfig,
  forYouParamConfig: ForYouParamConfig)
    extends ProductPipelineConfig[HomeMixerRequest, ForYouQuery, urt.TimelineResponse] {

  override val identifier: ProductPipelineIdentifier = ProductPipelineIdentifier("ForYou")

  override val product: Product = ForYouProduct

  override val paramConfig: ProductParamConfig = forYouParamConfig

  override def pipelineQueryTransformer(
    request: HomeMixerRequest,
    params: Params
  ): ForYouQuery = {
    val context = request.productContext match {
      case Some(context: ForYouProductContext) => context
      case _ => throw PipelineFailure(BadRequest, "ForYouProductContext not found")
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
          case topCursor @ UrtOrderedCursor(_, _, Some(TopCursor), _) =>
            val queryTime = debugOptions.flatMap(_.requestTimeOverride).getOrElse(Time.now)
            topCursor.copy(initialSortIndex = SortIndexBuilder.timeToId(queryTime))
          case cursor => cursor
        }
    }

    ForYouQuery(
      params = params,
      clientContext = request.clientContext,
      features = None,
      pipelineCursor = pipelineCursor,
      requestedMaxResults = Some(params(ServerMaxResultsParam)),
      debugOptions = debugOptions,
      deviceContext = context.deviceContext,
      seenTweetIds = context.seenTweetIds,
      dspClientContext = context.dspClientContext,
      pushToHomeTweetId = context.pushToHomeTweetId
    )
  }

  override val pipelines: Seq[PipelineConfig] = Seq(
    forYouTimelineScorerMixerPipelineConfig,
    forYouScoredTweetsMixerPipelineConfig,
    forYouPushToHomeMixerPipelineConfig
  )

  override def pipelineSelector(
    query: ForYouQuery
  ): ComponentIdentifier = {
    if (query.pushToHomeTweetId.isDefined && query.params(EnablePushToHomeMixerPipelineParam))
      forYouPushToHomeMixerPipelineConfig.identifier
    else if (query.params(EnableScoredTweetsMixerPipelineParam))
      forYouScoredTweetsMixerPipelineConfig.identifier
    else forYouTimelineScorerMixerPipelineConfig.identifier
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

  override val debugAccessPolicies: Set[AccessPolicy] = DefaultHomeMixerAccessPolicy
}
