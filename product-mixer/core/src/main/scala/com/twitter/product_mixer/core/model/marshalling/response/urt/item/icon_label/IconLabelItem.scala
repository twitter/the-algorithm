package com.twitter.product_mixer.core.model.marshalling.response.urt.item.icon_label

import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem

object IconLabelItem {
  val IconLabelEntryNamespace = EntryNamespace("iconlabel")
}

case class IconLabelItem(
  override val id: String,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  text: RichText,
  icon: Option[HorizonIcon])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = IconLabelItem.IconLabelEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
