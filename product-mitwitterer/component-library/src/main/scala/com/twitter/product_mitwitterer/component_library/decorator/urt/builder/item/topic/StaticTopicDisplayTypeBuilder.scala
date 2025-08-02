package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.topic

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.component_library.model.candidate.BaseTopicCandidate
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.item.topic.BaseTopicDisplayTypeBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.TopicDisplayType

case class StaticTopicDisplayTypeBuilder(
  displayType: TopicDisplayType)
    etwittertends BaseTopicDisplayTypeBuilder[PipelineQuery, BaseTopicCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: BaseTopicCandidate,
    candidateFeatures: FeatureMap
  ): Option[TopicDisplayType] = Some(displayType)
}
