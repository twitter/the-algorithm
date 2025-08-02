package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.promoted

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.PromotedMetadata
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BasePromotedMetadataBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[PromotedMetadata]
}
