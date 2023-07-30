package com.X.product_mixer.core.functional_component.decorator.urt.builder.stringcenter

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.pipeline.PipelineQuery

trait BaseStringCenterPlaceholderBuilder[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): Map[String, Any]
}
