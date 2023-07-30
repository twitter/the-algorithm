package com.X.product_mixer.component_library.decorator.urt.builder.item.topic

import com.X.product_mixer.component_library.decorator.urt.builder.item.topic.TopicCandidateUrtItemBuilder.TopicClientEventInfoElement
import com.X.product_mixer.component_library.model.candidate.TopicCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseUrlBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTileStyle
import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicTile
import com.X.product_mixer.core.pipeline.PipelineQuery

case class VerticalGridTopicCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, TopicCandidate],
  verticalGridItemTopicFunctionalityType: VerticalGridItemTopicFunctionalityType,
  verticalGridItemTileStyle: VerticalGridItemTileStyle,
  urlBuilder: Option[BaseUrlBuilder[Query, TopicCandidate]] = None,
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, TopicCandidate]
  ] = None)
    extends CandidateUrtEntryBuilder[Query, TopicCandidate, VerticalGridItem] {

  override def apply(
    query: Query,
    topicCandidate: TopicCandidate,
    candidateFeatures: FeatureMap
  ): VerticalGridItem = {
    VerticalGridItemTopicTile(
      id = topicCandidate.id,
      sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
      clientEventInfo = clientEventInfoBuilder(
        query,
        topicCandidate,
        candidateFeatures,
        Some(TopicClientEventInfoElement)),
      feedbackActionInfo =
        feedbackActionInfoBuilder.flatMap(_.apply(query, topicCandidate, candidateFeatures)),
      style = Some(verticalGridItemTileStyle),
      functionalityType = Some(verticalGridItemTopicFunctionalityType),
      url = urlBuilder.map(_.apply(query, topicCandidate, candidateFeatures))
    )
  }
}
