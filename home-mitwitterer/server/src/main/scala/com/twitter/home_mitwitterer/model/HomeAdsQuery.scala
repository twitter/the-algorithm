package com.twitter.home_mitwitterer.model

import com.twitter.adserver.thriftscala.RequestTriggerType
import com.twitter.home_mitwitterer.model.HomeFeatures.GetInitialFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.GetNewerFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.GetOlderFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.PollingFeature
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.component_library.model.query.ads.AdsQuery
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

/**
 * These are for feeds needed for ads only.
 */
trait HomeAdsQuery
    etwittertends AdsQuery
    with PipelineQuery
    with HasDeviceContetwittert
    with HasPipelineCursor[UrtOrderedCursor] {

  private val featureToRequestTriggerType = Seq(
    (GetInitialFeature, RequestTriggerType.Initial),
    (GetNewerFeature, RequestTriggerType.Scroll),
    (GetOlderFeature, RequestTriggerType.Scroll),
    (PollingFeature, RequestTriggerType.AutoRefresh)
  )

  override val autoplayEnabled: Option[Boolean] = deviceContetwittert.flatMap(_.autoplayEnabled)

  override def requestTriggerType: Option[RequestTriggerType] = {
    val features = this.features.getOrElse(FeatureMap.empty)

    featureToRequestTriggerType.collectFirst {
      case (feature, requestType) if features.get(feature) => Some(requestType)
    }.flatten
  }

  override val disableNsfwAvoidance: Option[Boolean] = Some(true)
}
