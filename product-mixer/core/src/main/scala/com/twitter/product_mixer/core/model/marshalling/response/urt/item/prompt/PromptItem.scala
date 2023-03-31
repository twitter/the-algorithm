package com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Callback

object PromptItem {
  val PromptEntryNamespace = EntryNamespace("relevanceprompt")
}

case class PromptItem(
  override val id: String,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo] = None,
  content: PromptContent,
  impressionCallbacks: Option[List[Callback]])
    extends TimelineItem {

  override val entryNamespace: EntryNamespace = PromptItem.PromptEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
