package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem

object MessagePromptItem {
  val MessagePromptEntryNamespace = EntryNamespace("messageprompt")
}

case class MessagePromptItem(
  override val id: String,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  override val isPinned: Option[Boolean],
  content: MessageContent,
  impressionCallbacks: Option[List[Callback]])
    etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace =
    MessagePromptItem.MessagePromptEntryNamespace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
