package com.twitter.product_mixer.core.functional_component.decorator.slice.builder

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.slice.SliceItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait CandidateSliceItemBuilder[
  -Query <: PipelineQuery,
  -BuilderInput <: UniversalNoun[Any],
  BuilderOutput <: SliceItem] {

  def apply(query: Query, candidate: BuilderInput, featureMap: FeatureMap): BuilderOutput
}
