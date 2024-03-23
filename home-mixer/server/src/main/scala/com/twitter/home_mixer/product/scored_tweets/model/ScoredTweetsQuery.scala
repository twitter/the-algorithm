package com.ExTwitter.home_mixer.product.scored_tweets.model

import com.ExTwitter.home_mixer.model.request.DeviceContext
import com.ExTwitter.home_mixer.model.request.HasDeviceContext
import com.ExTwitter.home_mixer.model.request.HasSeenTweetIds
import com.ExTwitter.home_mixer.model.request.ScoredTweetsProduct
import com.ExTwitter.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.model.marshalling.request._
import com.ExTwitter.product_mixer.core.pipeline.HasPipelineCursor
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.ExTwitter.product_mixer.core.quality_factor.QualityFactorStatus
import com.ExTwitter.timelines.configapi.Params

case class ScoredTweetsQuery(
  override val params: Params,
  override val clientContext: ClientContext,
  override val pipelineCursor: Option[UrtOrderedCursor],
  override val requestedMaxResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  override val deviceContext: Option[DeviceContext],
  override val seenTweetIds: Option[Seq[Long]],
  override val qualityFactorStatus: Option[QualityFactorStatus])
    extends PipelineQuery
    with HasPipelineCursor[UrtOrderedCursor]
    with HasDeviceContext
    with HasSeenTweetIds
    with HasQualityFactorStatus {
  override val product: Product = ScoredTweetsProduct

  override def withFeatureMap(features: FeatureMap): ScoredTweetsQuery =
    copy(features = Some(features))

  override def withQualityFactorStatus(
    qualityFactorStatus: QualityFactorStatus
  ): ScoredTweetsQuery = copy(qualityFactorStatus = Some(qualityFactorStatus))
}
