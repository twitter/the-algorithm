package com.twitter.product_mixer.core.model.marshalling.response.urt.item.commerce

import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.commerce.CommerceProductGroupItem.CommerceProductGroupEntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo

object CommerceProductGroupItem {
  val CommerceProductGroupEntryNamespace: EntryNamespace = EntryNamespace("commerce-product-group")
}

case class CommerceProductGroupItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo])
    extends TimelineItem {

  val entryNamespace: EntryNamespace = CommerceProductGroupEntryNamespace
  def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
