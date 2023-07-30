package com.X.product_mixer.core.functional_component.decorator.urt.builder

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.X.product_mixer.core.pipeline.PipelineQuery

trait CandidateUrtEntryBuilder[
  -Query <: PipelineQuery,
  -BuilderInput <: UniversalNoun[Any],
  BuilderOutput <: TimelineEntry] {

  def apply(query: Query, candidate: BuilderInput, candidateFeatures: FeatureMap): BuilderOutput
}
