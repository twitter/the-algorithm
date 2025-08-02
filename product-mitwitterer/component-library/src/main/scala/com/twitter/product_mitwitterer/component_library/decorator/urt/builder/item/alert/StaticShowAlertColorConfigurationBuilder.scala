package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.alert

import com.twitter.product_mitwitterer.component_library.model.candidate.ShowAlertCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.alert.BaseShowAlertColorConfigurationBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert.ShowAlertColorConfiguration
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class StaticShowAlertColorConfigurationBuilder[-Query <: PipelineQuery](
  configuration: ShowAlertColorConfiguration)
    etwittertends BaseShowAlertColorConfigurationBuilder[Query] {

  def apply(
    query: Query,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): ShowAlertColorConfiguration = configuration
}
