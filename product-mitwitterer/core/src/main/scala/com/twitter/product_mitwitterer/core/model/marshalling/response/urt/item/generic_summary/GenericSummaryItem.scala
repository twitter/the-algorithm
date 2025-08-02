package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.media.Media
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.PromotedMetadata
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.util.Time

object GenericSummaryItem {
  val GenericSummaryItemNamespace: EntryNamespace = EntryNamespace("genericsummary")
}

case class GenericSummaryItem(
  override val id: String,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  headline: RichTetwittert,
  displayType: GenericSummaryItemDisplayType,
  userAttributionIds: Seq[Long],
  media: Option[Media],
  contetwittert: Option[GenericSummaryContetwittert],
  timestamp: Option[Time],
  onClickAction: Option[GenericSummaryAction],
  promotedMetadata: Option[PromotedMetadata])
    etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace = GenericSummaryItem.GenericSummaryItemNamespace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
