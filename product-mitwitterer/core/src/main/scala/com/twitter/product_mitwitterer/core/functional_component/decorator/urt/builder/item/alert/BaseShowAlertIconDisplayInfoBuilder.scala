package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.alert

import com.twitter.product_mitwitterer.component_library.model.candidate.ShowAlertCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertIconDisplayInfo
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseShowAlertIconDisplayInfoBuilder[-Query <: PipelineQuery] {

  def apply(
    query: Query,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): Option[ShowAlertIconDisplayInfo]
}
