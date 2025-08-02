package com.twitter.home_mitwitterer.product.subscribed.model

import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasSeenTweetIds
import com.twitter.home_mitwitterer.model.request.SubscribedProduct
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.request._
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Params

case class SubscribedQuery(
  override val params: Params,
  override val clientContetwittert: ClientContetwittert,
  override val pipelineCursor: Option[UrtOrderedCursor],
  override val requestedMatwitterResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  override val deviceContetwittert: Option[DeviceContetwittert],
  override val seenTweetIds: Option[Seq[Long]])
    etwittertends PipelineQuery
    with HasPipelineCursor[UrtOrderedCursor]
    with HasDeviceContetwittert
    with HasSeenTweetIds {
  override val product: Product = SubscribedProduct

  override def withFeatureMap(features: FeatureMap): SubscribedQuery =
    copy(features = Some(features))
}
