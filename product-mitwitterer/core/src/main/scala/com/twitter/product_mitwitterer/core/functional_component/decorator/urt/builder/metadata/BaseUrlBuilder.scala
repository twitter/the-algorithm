package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata

import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap

trait BaseUrlBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): Url
}
