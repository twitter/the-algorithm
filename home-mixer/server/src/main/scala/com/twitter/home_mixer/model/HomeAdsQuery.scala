package com.ExTwitter.home_mixer.model

import com.ExTwitter.adserver.thriftscala.RequestTriggerType
import com.ExTwitter.home_mixer.model.HomeFeatures.GetInitialFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.GetNewerFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.GetOlderFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.PollingFeature
import com.ExTwitter.home_mixer.model.request.HasDeviceContext
import com.ExTwitter.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.ExTwitter.product_mixer.component_library.model.query.ads.AdsQuery
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.pipeline.HasPipelineCursor
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

/**
 * These are for feeds needed for ads only.
 */
trait HomeAdsQuery
    extends AdsQuery
    with PipelineQuery
    with HasDeviceContext
    with HasPipelineCursor[UrtOrderedCursor] {

  private val featureToRequestTriggerType = Seq(
    (GetInitialFeature, RequestTriggerType.Initial),
    (GetNewerFeature, RequestTriggerType.Scroll),
    (GetOlderFeature, RequestTriggerType.Scroll),
    (PollingFeature, RequestTriggerType.AutoRefresh)
  )

  override val autoplayEnabled: Option[Boolean] = deviceContext.flatMap(_.autoplayEnabled)

  override def requestTriggerType: Option[RequestTriggerType] = {
    val features = this.features.getOrElse(FeatureMap.empty)

    featureToRequestTriggerType.collectFirst {
      case (feature, requestType) if features.get(feature) => Some(requestType)
    }.flatten
  }

  override val disableNsfwAvoidance: Option[Boolean] = Some(true)
}
