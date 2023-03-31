package com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem

object TopicItem {
  val TopicEntryNamespace = EntryNamespace("topic")
}

case class TopicItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  topicFunctionalityType: Option[TopicFunctionalityType],
  topicDisplayType: Option[TopicDisplayType])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = TopicItem.TopicEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
