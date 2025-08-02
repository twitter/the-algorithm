package com.twitter.home_mitwitterer.product.following.model

import com.twitter.adserver.thriftscala.HomeTimelineType
import com.twitter.adserver.thriftscala.TimelineRequestParams
import com.twitter.home_mitwitterer.model.HomeAdsQuery
import com.twitter.dspbidder.commons.{thriftscala => dsp}
import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasSeenTweetIds
import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.onboarding.task.service.{thriftscala => ots}
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.fletwitterible_injection_pipeline.transformer.HasFlipInjectionParams
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.request._
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Params

case class FollowingQuery(
  override val params: Params,
  override val clientContetwittert: ClientContetwittert,
  override val pipelineCursor: Option[UrtOrderedCursor],
  override val requestedMatwitterResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  override val deviceContetwittert: Option[DeviceContetwittert],
  override val seenTweetIds: Option[Seq[Long]],
  override val dspClientContetwittert: Option[dsp.DspClientContetwittert])
    etwittertends PipelineQuery
    with HasPipelineCursor[UrtOrderedCursor]
    with HasDeviceContetwittert
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
