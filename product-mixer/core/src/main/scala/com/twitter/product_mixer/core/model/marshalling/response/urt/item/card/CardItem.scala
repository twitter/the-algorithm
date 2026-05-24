package com.twitter.product_mixer.core.model.marshalling.response.urt.item.card

import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url

object CardItem {
  val CardEntryNamespace = EntryNamespace("card")
}

case class CardItem(
  override val id: String,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  cardUrl: String,
  text: Option[String],
  subtext: Option[String],
  url: Option[Url],
  displayType: Option[CardDisplayType])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = CardItem.CardEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
