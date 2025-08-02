package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.vertical_grid_item

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem

sealed trait VerticalGridItem etwittertends TimelineItem

object VerticalGridItemTopicTile {
  val VerticalGridItemTopicTileEntryNamespace = EntryNamespace("verticalgriditemtopictile")
}

case class VerticalGridItemTopicTile(
  override val id: Long,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  style: Option[VerticalGridItemTileStyle],
  functionalityType: Option[VerticalGridItemTopicFunctionalityType],
  url: Option[Url])
    etwittertends VerticalGridItem {
  override val entryNamespace: EntryNamespace =
    VerticalGridItemTopicTile.VerticalGridItemTopicTileEntryNamespace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
