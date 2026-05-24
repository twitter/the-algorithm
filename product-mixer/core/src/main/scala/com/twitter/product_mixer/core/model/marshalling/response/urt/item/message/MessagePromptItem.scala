package com.twitter.product_mixer.core.model.marshalling.response.urt.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem

object MessagePromptItem {
  val MessagePromptEntryNamespace = EntryNamespace("messageprompt")
}

case class MessagePromptItem(
  override val id: String,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  override val isPinned: Option[Boolean],
  content: MessageContent,
  impressionCallbacks: Option[List[Callback]])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace =
    MessagePromptItem.MessagePromptEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
