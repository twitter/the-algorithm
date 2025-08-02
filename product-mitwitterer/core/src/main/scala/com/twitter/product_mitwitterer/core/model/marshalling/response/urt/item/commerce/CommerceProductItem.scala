package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.commerce

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.commerce.CommerceProductItem.CommerceProductEntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo

object CommerceProductItem {
  val CommerceProductEntryNamespace: EntryNamespace = EntryNamespace("commerce-product")
}

case class CommerceProductItem(
  override val id: Long,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo])
    etwittertends TimelineItem {

  val entryNamespace: EntryNamespace = CommerceProductEntryNamespace
  def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
