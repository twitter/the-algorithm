package com.twitter.product_mixer.core.model.marshalling.response.urt.item.tombstone

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem

object TombstoneItem {
  val TombstoneEntryNamespace = EntryNamespace("tombstone")
}

case class TombstoneItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  tombstoneDisplayType: TombstoneDisplayType,
  tombstoneInfo: Option[TombstoneInfo],
  tweet: Option[TweetItem])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = TombstoneItem.TombstoneEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
