package com.X.product_mixer.component_library.decorator.urt.builder.item.alert

import com.X.product_mixer.component_library.model.candidate.ShowAlertCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.item.alert.BaseDurationBuilder
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.timelines.configapi.Param
import com.X.util.Duration

case class DurationParamBuilder(
  durationParam: Param[Duration])
    extends BaseDurationBuilder[PipelineQuery] {

  def apply(
    query: PipelineQuery,
    candidate: ShowAlertCandidate,
    features: FeatureMap
  ): Option[Duration] =
    Some(query.params(durationParam))
}
