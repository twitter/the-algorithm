package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.tile

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.tile.TileCandidateUrtItemBuilder.TopicTileClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.PromptCarouselTileCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile.StandardTileContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile.TileItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object TileCandidateUrtItemBuilder {
  val TopicTileClientEventInfoElement: String = "tile"
}

case class TileCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, PromptCarouselTileCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, PromptCarouselTileCandidate]
  ] = None)
    etwittertends CandidateUrtEntryBuilder[Query, PromptCarouselTileCandidate, TileItem] {

  override def apply(
    query: Query,
    tileCandidate: PromptCarouselTileCandidate,
    candidateFeatures: FeatureMap
  ): TileItem = TileItem(
    id = tileCandidate.id,
    sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      tileCandidate,
      candidateFeatures,
      Some(TopicTileClientEventInfoElement)),
    title = "", //This data is ignored do
    supportingTetwittert = "",
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, tileCandidate, candidateFeatures)),
    image = None,
    url = None,
    content = StandardTileContent(
      title = "",
      supportingTetwittert = "",
      badge = None
    )
  )
}
