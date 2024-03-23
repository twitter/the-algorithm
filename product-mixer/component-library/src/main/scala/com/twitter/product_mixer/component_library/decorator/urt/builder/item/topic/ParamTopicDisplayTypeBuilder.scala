package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.topic

import com.ExTwitter.product_mixer.component_library.model.candidate.TopicCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.timelines.configapi.FSEnumParam
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.topic.BasicTopicDisplayType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.topic.PillTopicDisplayType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.topic.NoIconTopicDisplayType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.topic.PillWithoutActionIconDisplayType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.topic.TopicDisplayType
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.item.topic.BaseTopicDisplayTypeBuilder

object TopicCandidateDisplayType extends Enumeration {
  type TopicDisplayType = Value

  val Basic = Value
  val Pill = Value
  val NoIcon = Value
  val PillWithoutActionIcon = Value
}

case class ParamTopicDisplayTypeBuilder(
  displayTypeParam: FSEnumParam[TopicCandidateDisplayType.type])
    extends BaseTopicDisplayTypeBuilder[PipelineQuery, TopicCandidate] {

  override def apply(
    query: PipelineQuery,
    candidate: TopicCandidate,
    candidateFeatures: FeatureMap
  ): Option[TopicDisplayType] = {
    val displayType = query.params(displayTypeParam)
    displayType match {
      case TopicCandidateDisplayType.Basic => Some(BasicTopicDisplayType)
      case TopicCandidateDisplayType.Pill => Some(PillTopicDisplayType)
      case TopicCandidateDisplayType.NoIcon =>
        Some(NoIconTopicDisplayType)
      case TopicCandidateDisplayType.PillWithoutActionIcon => Some(PillWithoutActionIconDisplayType)
    }
  }
}
