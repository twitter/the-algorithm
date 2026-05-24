package com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait BaseStr[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): String
}
