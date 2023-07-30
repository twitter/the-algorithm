package com.X.home_mixer.product.subscribed.model

import com.X.home_mixer.model.request.DeviceContext
import com.X.home_mixer.model.request.HasDeviceContext
import com.X.home_mixer.model.request.HasSeenTweetIds
import com.X.home_mixer.model.request.SubscribedProduct
import com.X.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.marshalling.request._
import com.X.product_mixer.core.pipeline.HasPipelineCursor
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.Params

case class SubscribedQuery(
  override val params: Params,
  override val clientContext: ClientContext,
  override val pipelineCursor: Option[UrtOrderedCursor],
  override val requestedMaxResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  override val deviceContext: Option[DeviceContext],
  override val seenTweetIds: Option[Seq[Long]])
    extends PipelineQuery
    with HasPipelineCursor[UrtOrderedCursor]
    with HasDeviceContext
    with HasSeenTweetIds {
  override val product: Product = SubscribedProduct

  override def withFeatureMap(features: FeatureMap): SubscribedQuery =
    copy(features = Some(features))
}
