package com.twitter.home_mixer.product.for_you.model

import com.twitter.adserver.thriftscala.HomeTimelineType
import com.twitter.adserver.thriftscala.TimelineRequestParams
import com.twitter.dspbidder.commons.{thriftscala => dsp}
import com.twitter.home_mixer.model.HomeAdsQuery
import com.twitter.home_mixer.model.request.DeviceContext
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.model.request.HasSeenTweetIds
import com.twitter.onboarding.task.service.{thriftscala => ots}
import com.twitter.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer.HasFlipInjectionParams
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.request._
import com.twitter.product_mixer.core.pipeline.HasPipelineCursor
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Params

case class ForYouQuery(
  override val params: Params,
  override val clientContext: ClientContext,
  override val pipelineCursor: Option[UrtOrderedCursor],
  override val requestedMaxResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  override val deviceContext: Option[DeviceContext],
  override val seenTweetIds: Option[Seq[Long]],
  override val dspClientContext: Option[dsp.DspClientContext])
    extends PipelineQuery
    with HasPipelineCursor[UrtOrderedCursor]
    with HasDeviceContext
    with HasSeenTweetIds
    with HasFlipInjectionParams
    with HomeAdsQuery {
  override val product: Product = ForYouProduct

  override def withFeatureMap(features: FeatureMap): ForYouQuery =
    copy(features = Some(features))

  override val timelineRequestParams: Option[TimelineRequestParams] =
    Some(TimelineRequestParams(homeTimelineType = Some(HomeTimelineType.Home)))

  // Fields below are used for FLIP Injection in Onboarding Task Service (OTS)
  override val displayLocation: ots.DisplayLocation = ots.DisplayLocation.HomeTimeline
  override val rankingDisablerWithLatestControlsAvailable: Option[Boolean] = None
  override val isEmptyState: Option[Boolean] = None
  override val isFirstRequestAfterSignup: Option[Boolean] = None
  override val isEndOfTimeline: Option[Boolean] = None
  override val timelineId: Option[Long] = None
}
