package com.X.product_mixer.core.model.marshalling.response.urt.item.commerce

import com.X.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.commerce.CommerceProductItem.CommerceProductEntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo

object CommerceProductItem {
  val CommerceProductEntryNamespace: EntryNamespace = EntryNamespace("commerce-product")
}

case class CommerceProductItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo])
    extends TimelineItem {

  val entryNamespace: EntryNamespace = CommerceProductEntryNamespace
  def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
