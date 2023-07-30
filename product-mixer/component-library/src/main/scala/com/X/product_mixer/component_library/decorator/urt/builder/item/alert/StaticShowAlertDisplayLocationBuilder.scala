package com.X.product_mixer.component_library.decorator.urt.builder.item.alert

import com.X.product_mixer.component_library.model.candidate.ShowAlertCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.item.alert.BaseShowAlertDisplayLocationBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertDisplayLocation
import com.X.product_mixer.core.pipeline.PipelineQuery

case class StaticShowAlertDisplayLocationBuilder[-Query <: PipelineQuery](
  location: ShowAlertDisplayLocation)
    extends BaseShowAlertDisplayLocationBuilder[Query] {

  def apply(
    query: Query,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): ShowAlertDisplayLocation = location
}
