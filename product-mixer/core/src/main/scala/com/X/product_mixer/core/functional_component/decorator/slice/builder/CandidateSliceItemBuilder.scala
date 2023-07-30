package com.X.product_mixer.core.functional_component.decorator.slice.builder

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.slice.SliceItem
import com.X.product_mixer.core.pipeline.PipelineQuery

trait CandidateSliceItemBuilder[
  -Query <: PipelineQuery,
  -BuilderInput <: UniversalNoun[Any],
  BuilderOutput <: SliceItem] {

  def apply(query: Query, candidate: BuilderInput, featureMap: FeatureMap): BuilderOutput
}
