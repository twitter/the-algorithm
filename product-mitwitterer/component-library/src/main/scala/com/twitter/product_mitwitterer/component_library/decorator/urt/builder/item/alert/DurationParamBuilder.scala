package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.alert

import com.twitter.product_mitwitterer.component_library.model.candidate.ShowAlertCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.alert.BaseDurationBuilder
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

case class DurationParamBuilder(
  durationParam: Param[Duration])
    etwittertends BaseDurationBuilder[PipelineQuery] {

  def apply(
    query: PipelineQuery,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): Option[Duration] =
    Some(query.params(durationParam))
}
