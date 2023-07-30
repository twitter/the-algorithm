package com.X.product_mixer.core.functional_component.decorator.urt.builder.item.topic

import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicFunctionalityType

trait BaseTopicFunctionalityTypeBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(
    query: PipelineQuery,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[TopicFunctionalityType]
}
