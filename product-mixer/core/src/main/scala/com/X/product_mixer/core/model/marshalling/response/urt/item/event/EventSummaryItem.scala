package com.X.product_mixer.core.model.marshalling.response.urt.item.event

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.X.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem

object EventSummaryItem {
  val EventSummaryItemEntryNamespace = EntryNamespace("eventsummary")
}

case class EventSummaryItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  title: String,
  displayType: EventSummaryDisplayType,
  url: Url,
  image: Option[ImageVariant],
  timeString: Option[String])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace =
    EventSummaryItem.EventSummaryItemEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
