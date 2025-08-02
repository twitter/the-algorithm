package com.twitter.home_mitwitterer.product.list_tweets.model

import com.twitter.adserver.thriftscala.HomeTimelineType
import com.twitter.adserver.thriftscala.TimelineRequestParams
import com.twitter.dspbidder.commons.{thriftscala => dsp}
import com.twitter.home_mitwitterer.model.HomeAdsQuery
import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasListId
import com.twitter.home_mitwitterer.model.request.ListTweetsProduct
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.request._
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Params

case class ListTweetsQuery(
  override val params: Params,
  override val clientContetwittert: ClientContetwittert,
  override val pipelineCursor: Option[UrtOrderedCursor],
  override val requestedMatwitterResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  override val listId: Long,
  override val deviceContetwittert: Option[DeviceContetwittert],
  override val dspClientContetwittert: Option[dsp.DspClientContetwittert])
    etwittertends PipelineQuery
    with HasPipelineCursor[UrtOrderedCursor]
    with HasListId
    with HomeAdsQuery {
  override val product: Product = ListTweetsProduct

  override def withFeatureMap(features: FeatureMap): ListTweetsQuery =
    copy(features = Some(features))

  override val timelineRequestParams: Option[TimelineRequestParams] =
    Some(TimelineRequestParams(homeTimelineType = Some(HomeTimelineType.HomeLatest)))
}
