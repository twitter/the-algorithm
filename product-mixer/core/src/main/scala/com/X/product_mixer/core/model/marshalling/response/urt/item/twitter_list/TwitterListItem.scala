package com.X.product_mixer.core.model.marshalling.response.urt.item.X_list

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.X.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem

object XListItem {
  val ListEntryNamespace = EntryNamespace("list")
}

case class XListItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  displayType: Option[XListDisplayType])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = XListItem.ListEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
