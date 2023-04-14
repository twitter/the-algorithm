try {
package com.twitter.product_mixer.component_library.decorator.urt.builder.item.alert

import com.twitter.product_mixer.component_library.model.candidate.ShowAlertCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.item.alert.BaseShowAlertIconDisplayInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertIconDisplayInfo
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class StaticShowAlertIconDisplayInfoBuilder[-Query <: PipelineQuery](
  iconDisplayInfo: ShowAlertIconDisplayInfo)
    extends BaseShowAlertIconDisplayInfoBuilder[Query] {

  def apply(
    query: Query,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): Option[ShowAlertIconDisplayInfo] = Some(iconDisplayInfo)
}

} catch {
  case e: Exception =>
}
