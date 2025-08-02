package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.card

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url

object CardItem {
  val CardEntryNamespace = EntryNamespace("card")
}

case class CardItem(
  override val id: String,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  cardUrl: String,
  tetwittert: Option[String],
  subtetwittert: Option[String],
  url: Option[Url],
  displayType: Option[CardDisplayType])
    etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace = CardItem.CardEntryNamespace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
