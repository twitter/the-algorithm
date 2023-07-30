package com.X.product_mixer.component_library.decorator.urt.builder.item.topic

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.component_library.model.candidate.BaseTopicCandidate
import com.X.product_mixer.core.functional_component.decorator.urt.builder.item.topic.BaseTopicDisplayTypeBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicDisplayType

case class StaticTopicDisplayTypeBuilder(
  displayType: TopicDisplayType)
    extends BaseTopicDisplayTypeBuilder[PipelineQuery, BaseTopicCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: BaseTopicCandidate,
    candidateFeatures: FeatureMap
  ): Option[TopicDisplayType] = Some(displayType)
}
