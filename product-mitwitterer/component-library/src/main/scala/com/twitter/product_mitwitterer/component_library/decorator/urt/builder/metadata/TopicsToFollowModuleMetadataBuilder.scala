package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata

import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.timeline_module.BaseModuleMetadataBuilder
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.GridCarouselMetadata
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleMetadata
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

object TopicsToFollowModuleMetadataBuilder {

  val TopicsPerRow = 7

  /*
   * rows = min(MAtwitter_NUM_ROWS, # topics / TOPICS_PER_ROW)
   * where TOPICS_PER_ROW = 7
   */
  def getCarouselRowCount(topicsCount: Int, matwitterCarouselRows: Int): Int =
    Math.min(matwitterCarouselRows, (topicsCount / TopicsPerRow) + 1)
}

case class TopicsToFollowModuleMetadataBuilder(matwitterCarouselRowsParam: Param[Int])
    etwittertends BaseModuleMetadataBuilder[PipelineQuery, UniversalNoun[Any]] {

  import TopicsToFollowModuleMetadataBuilder._

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UniversalNoun[Any]]]
  ): ModuleMetadata = {
    val rowCount = getCarouselRowCount(candidates.size, query.params(matwitterCarouselRowsParam))
    ModuleMetadata(
      adsMetadata = None,
      conversationMetadata = None,
      gridCarouselMetadata = Some(GridCarouselMetadata(numRows = Some(rowCount)))
    )
  }
}
