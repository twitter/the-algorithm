package com.X.home_mixer.product.following.model

import com.X.adserver.thriftscala.HomeTimelineType
import com.X.adserver.thriftscala.TimelineRequestParams
import com.X.home_mixer.model.HomeAdsQuery
import com.X.dspbidder.commons.{thriftscala => dsp}
import com.X.home_mixer.model.request.DeviceContext
import com.X.home_mixer.model.request.HasDeviceContext
import com.X.home_mixer.model.request.HasSeenTweetIds
import com.X.home_mixer.model.request.FollowingProduct
import com.X.onboarding.task.service.{thriftscala => ots}
import com.X.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.X.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer.HasFlipInjectionParams
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.marshalling.request._
import com.X.product_mixer.core.pipeline.HasPipelineCursor
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.Params

case class FollowingQuery(
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
  override val product: Product = FollowingProduct

  override def withFeatureMap(features: FeatureMap): FollowingQuery =
    copy(features = Some(features))

  override val timelineRequestParams: Option[TimelineRequestParams] =
    Some(TimelineRequestParams(homeTimelineType = Some(HomeTimelineType.HomeLatest)))

  // Fields below are used for FLIP Injection in Onboarding Task Service (OTS)
  override val displayLocation: ots.DisplayLocation = ots.DisplayLocation.HomeLatestTimeline
  override val rankingDisablerWithLatestControlsAvailable: Option[Boolean] = None
  override val isEmptyState: Option[Boolean] = None
  override val isFirstRequestAfterSignup: Option[Boolean] = None
  override val isEndOfTimeline: Option[Boolean] = None
  override val timelineId: Option[Long] = None
}
