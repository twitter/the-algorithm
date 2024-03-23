package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.tile

import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.tile.TileCandidateUrtItemBuilder.TopicTileClientEventInfoElement
import com.ExTwitter.product_mixer.component_library.model.candidate.PromptCarouselTileCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.tile.StandardTileContent
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.tile.TileItem
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

object TileCandidateUrtItemBuilder {
  val TopicTileClientEventInfoElement: String = "tile"
}

case class TileCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, PromptCarouselTileCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, PromptCarouselTileCandidate]
  ] = None)
    extends CandidateUrtEntryBuilder[Query, PromptCarouselTileCandidate, TileItem] {

  override def apply(
    query: Query,
    tileCandidate: PromptCarouselTileCandidate,
    candidateFeatures: FeatureMap
  ): TileItem = TileItem(
    id = tileCandidate.id,
    sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      tileCandidate,
      candidateFeatures,
      Some(TopicTileClientEventInfoElement)),
    title = "", //This data is ignored do
    supportingText = "",
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, tileCandidate, candidateFeatures)),
    image = None,
    url = None,
    content = StandardTileContent(
      title = "",
      supportingText = "",
      badge = None
    )
  )
}
