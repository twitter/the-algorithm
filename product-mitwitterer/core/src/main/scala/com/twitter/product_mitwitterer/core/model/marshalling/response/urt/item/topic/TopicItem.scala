package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem

object TopicItem {
  val TopicEntryNamespace = EntryNamespace("topic")
}

case class TopicItem(
  override val id: Long,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  topicFunctionalityType: Option[TopicFunctionalityType],
  topicDisplayType: Option[TopicDisplayType])
    etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace = TopicItem.TopicEntryNamespace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
