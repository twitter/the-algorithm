package com.twitter.home_mitwitterer.product.scored_tweets.model

import com.twitter.home_mitwitterer.model.request.DeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.model.request.HasSeenTweetIds
import com.twitter.home_mitwitterer.model.request.ScoredTweetsProduct
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.request._
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorStatus
import com.twitter.timelines.configapi.Params

case class ScoredTweetsQuery(
  override val params: Params,
  override val clientContetwittert: ClientContetwittert,
  override val pipelineCursor: Option[UrtOrderedCursor],
  override val requestedMatwitterResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  override val deviceContetwittert: Option[DeviceContetwittert],
  override val seenTweetIds: Option[Seq[Long]],
  override val qualityFactorStatus: Option[QualityFactorStatus])
    etwittertends PipelineQuery
    with HasPipelineCursor[UrtOrderedCursor]
    with HasDeviceContetwittert
    with HasSeenTweetIds
    with HasQualityFactorStatus {
  override val product: Product = ScoredTweetsProduct

  override def withFeatureMap(features: FeatureMap): ScoredTweetsQuery =
    copy(features = Some(features))

  override def withQualityFactorStatus(
    qualityFactorStatus: QualityFactorStatus
  ): ScoredTweetsQuery = copy(qualityFactorStatus = Some(qualityFactorStatus))
}
