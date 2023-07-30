package com.X.product_mixer.component_library.decorator.urt.builder.item.topic

import com.X.product_mixer.component_library.model.candidate.TopicCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.BasicTopicFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.PivotTopicFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.RecommendationTopicFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicFunctionalityType
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.functional_component.decorator.urt.builder.item.topic.BaseTopicFunctionalityTypeBuilder
import com.X.timelines.configapi.FSEnumParam

object TopicFunctionalityTypeParamValue extends Enumeration {
  type TopicFunctionalityType = Value

  val Basic = Value
  val Pivot = Value
  val Recommendation = Value
}

case class ParamTopicFunctionalityTypeBuilder(
  functionalityTypeParam: FSEnumParam[TopicFunctionalityTypeParamValue.type])
    extends BaseTopicFunctionalityTypeBuilder[PipelineQuery, TopicCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: TopicCandidate,
    candidateFeatures: FeatureMap
  ): Option[TopicFunctionalityType] = {
    val functionalityType = query.params(functionalityTypeParam)
    functionalityType match {
      case TopicFunctionalityTypeParamValue.Basic => Some(BasicTopicFunctionalityType)
      case TopicFunctionalityTypeParamValue.Pivot => Some(PivotTopicFunctionalityType)
      case TopicFunctionalityTypeParamValue.Recommendation =>
        Some(RecommendationTopicFunctionalityType)
    }
  }
}
