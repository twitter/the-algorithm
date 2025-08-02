package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.alert

import com.twitter.product_mitwitterer.component_library.model.candidate.ShowAlertCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertColorConfiguration
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseShowAlertColorConfigurationBuilder[-Query <: PipelineQuery] {

  def apply(
    query: Query,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): ShowAlertColorConfiguration
}
