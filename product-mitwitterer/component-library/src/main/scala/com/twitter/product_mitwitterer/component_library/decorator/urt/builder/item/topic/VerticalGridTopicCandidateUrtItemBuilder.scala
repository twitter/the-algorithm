package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.topic

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.topic.TopicCandidateUrtItemBuilder.TopicClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.TopicCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseUrlBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTileStyle
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItemTopicTile
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class VerticalGridTopicCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, TopicCandidate],
  verticalGridItemTopicFunctionalityType: VerticalGridItemTopicFunctionalityType,
  verticalGridItemTileStyle: VerticalGridItemTileStyle,
  urlBuilder: Option[BaseUrlBuilder[Query, TopicCandidate]] = None,
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, TopicCandidate]
  ] = None)
    etwittertends CandidateUrtEntryBuilder[Query, TopicCandidate, VerticalGridItem] {

  override def apply(
    query: Query,
    topicCandidate: TopicCandidate,
    candidateFeatures: FeatureMap
  ): VerticalGridItem = {
    VerticalGridItemTopicTile(
      id = topicCandidate.id,
      sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
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
