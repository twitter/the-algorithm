package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.topic

import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.component_library.model.candidate.BaseTopicCandidate
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.item.topic.BaseTopicFunctionalityTypeBuilder
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.topic.TopicFunctionalityType
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

case class StaticTopicFunctionalityTypeBuilder(
  functionalityType: TopicFunctionalityType)
    extends BaseTopicFunctionalityTypeBuilder[PipelineQuery, BaseTopicCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: BaseTopicCandidate,
    candidateFeatures: FeatureMap
  ): Option[TopicFunctionalityType] = Some(functionalityType)
}
