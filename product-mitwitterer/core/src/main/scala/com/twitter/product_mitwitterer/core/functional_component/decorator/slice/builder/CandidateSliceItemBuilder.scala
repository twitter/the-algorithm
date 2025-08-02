package com.twitter.product_mitwitterer.core.functional_component.decorator.slice.builder

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.SliceItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait CandidateSliceItemBuilder[
  -Query <: PipelineQuery,
  -BuilderInput <: UniversalNoun[Any],
  BuilderOutput <: SliceItem] {

  def apply(query: Query, candidate: BuilderInput, featureMap: FeatureMap): BuilderOutput
}
