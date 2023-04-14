try {
package com.twitter.product_mixer.core.functional_component.decorator.urt.builder.item.alert

import com.twitter.product_mixer.component_library.model.candidate.ShowAlertCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.response.urt.alert.ShowAlertDisplayLocation
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait BaseShowAlertDisplayLocationBuilder[-Query <: PipelineQuery] {

  def apply(
    query: Query,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): ShowAlertDisplayLocation
}

} catch {
  case e: Exception =>
}
