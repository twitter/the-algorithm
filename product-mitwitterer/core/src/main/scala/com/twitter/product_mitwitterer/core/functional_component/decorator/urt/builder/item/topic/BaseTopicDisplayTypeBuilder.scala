package com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.topic

import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.TopicDisplayType

trait BaseTopicDisplayTypeBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: PipelineQuery,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[TopicDisplayType]
}
