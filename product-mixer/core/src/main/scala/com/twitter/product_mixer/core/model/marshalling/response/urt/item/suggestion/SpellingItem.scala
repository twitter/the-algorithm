package com.twitter.product_mixer.core.model.marshalling.response.urt.item.suggestion

import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo

object SpellingItem {
  val SpellingEntryNamespace = EntryNamespace("spelling")
}

/**
 * Represents a Spelling Suggestion URT item. This is primary used by Search timelines for
 * displaying Spelling correction information.
 *
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/Spelling.html
 */
case class SpellingItem(
  override val id: String,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  textResult: TextResult,
  spellingActionType: Option[SpellingActionType],
  originalQuery: Option[String])
    extends TimelineItem {

  override val entryNamespace: EntryNamespace = SpellingItem.SpellingEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
