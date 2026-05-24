package com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata

import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap

trait BaseUrlBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): Url
}
