package com.X.product_mixer.component_library.decorator.urt.builder.item.alert

import com.X.product_mixer.component_library.model.candidate.ShowAlertCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.item.alert.BaseShowAlertIconDisplayInfoBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertIconDisplayInfo
import com.X.product_mixer.core.pipeline.PipelineQuery

case class StaticShowAlertIconDisplayInfoBuilder[-Query <: PipelineQuery](
  iconDisplayInfo: ShowAlertIconDisplayInfo)
    extends BaseShowAlertIconDisplayInfoBuilder[Query] {

  def apply(
    query: Query,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): Option[ShowAlertIconDisplayInfo] = Some(iconDisplayInfo)
}
