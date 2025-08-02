package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait BaseFeedbackActionInfoBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo]
}
