package com.twitter.product_mixer.core.model.marshalling.response.urt.item.label

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem

object LabelItem {
  val LabelEntryNamespace = EntryNamespace("label")
}

case class LabelItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  text: String,
  subtext: Option[String],
  disclosureIndicator: Option[Boolean],
  url: Option[Url],
  displayType: Option[LabelDisplayType])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = LabelItem.LabelEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
