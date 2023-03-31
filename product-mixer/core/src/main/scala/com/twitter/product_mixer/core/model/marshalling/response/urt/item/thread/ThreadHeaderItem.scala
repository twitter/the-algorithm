package com.twitter.product_mixer.core.model.marshalling.response.urt.item.thread

import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo

object ThreadHeaderItem {
  val ThreadHeaderEntryNamespace = EntryNamespace("threadheader")
}

case class ThreadHeaderItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  override val isPinned: Option[Boolean],
  content: ThreadHeaderContent)
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = ThreadHeaderItem.ThreadHeaderEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
