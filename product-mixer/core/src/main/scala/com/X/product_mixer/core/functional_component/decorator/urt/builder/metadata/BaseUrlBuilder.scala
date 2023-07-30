package com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata

import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.feature.featuremap.FeatureMap

trait BaseUrlBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): Url
}
