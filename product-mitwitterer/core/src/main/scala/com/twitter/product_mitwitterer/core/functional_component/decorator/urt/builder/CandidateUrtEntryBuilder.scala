package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait CandidateUrtEntryBuilder[
  -Query <: PipelineQuery,
  -BuilderInput <: UniversalNoun[Any],
  BuilderOutput <: TimelineEntry] {

  def apply(query: Query, candidate: BuilderInput, candidateFeatures: FeatureMap): BuilderOutput
}
