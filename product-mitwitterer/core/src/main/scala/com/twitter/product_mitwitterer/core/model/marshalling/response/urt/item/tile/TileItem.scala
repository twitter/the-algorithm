package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem

object TileItem {
  val TileEntryNamespace = EntryNamespace("tile")
}

case class TileItem(
  override val id: Long,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  title: String,
  supportingTetwittert: String,
  url: Option[Url],
  image: Option[ImageVariant],
  content: TileContent)
    etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace = TileItem.TileEntryNamespace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
