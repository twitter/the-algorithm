package com.twitter.product_mixer.core.model.marshalling.response.urt.item.generic_summary

import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.media.Media
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.PromotedMetadata
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.twitter.util.Time

object GenericSummaryItem {
  val GenericSummaryItemNamespace: EntryNamespace = EntryNamespace("genericsummary")
}

case class GenericSummaryItem(
  override val id: String,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  headline: RichText,
  displayType: GenericSummaryItemDisplayType,
  userAttributionIds: Seq[Long],
  media: Option[Media],
  context: Option[GenericSummaryContext],
  timestamp: Option[Time],
  onClickAction: Option[GenericSummaryAction],
  promotedMetadata: Option[PromotedMetadata])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = GenericSummaryItem.GenericSummaryItemNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
