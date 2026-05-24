package com.twitter.product_mixer.component_library.decorator.urt.builder.metadata

import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleMetadataBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.GridCarouselMetadata
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleMetadata
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

object TopicsToFollowModuleMetadataBuilder {

  val TopicsPerRow = 7

  /*
   * rows = min(MAX_NUM_ROWS, # topics / TOPICS_PER_ROW)
   * where TOPICS_PER_ROW = 7
   */
  def getCarouselRowCount(topicsCount: Int, maxCarouselRows: Int): Int =
    Math.min(maxCarouselRows, (topicsCount / TopicsPerRow) + 1)
}

case class TopicsToFollowModuleMetadataBuilder(maxCarouselRowsParam: Param[Int])
    extends BaseModuleMetadataBuilder[PipelineQuery, UniversalNoun[Any]] {

  import TopicsToFollowModuleMetadataBuilder._

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UniversalNoun[Any]]]
  ): ModuleMetadata = {
    val rowCount = getCarouselRowCount(candidates.size, query.params(maxCarouselRowsParam))
    ModuleMetadata(
      adsMetadata = None,
      conversationMetadata = None,
      gridCarouselMetadata = Some(GridCarouselMetadata(numRows = Some(rowCount)))
    )
  }
}
