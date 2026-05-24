package com.twitter.product_mixer.core.model.marshalling.response.urt.item.trend

import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.PromotedMetadata

object TrendItem {
  val TrendItemEntryNamespace = EntryNamespace("trend")
}

case class GroupedTrend(trendName: String, url: Url)

case class TrendItem(
  override val id: String,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  normalizedTrendName: String,
  trendName: String,
  url: Url,
  description: Option[String],
  metaDescription: Option[String],
  tweetCount: Option[Int],
  domainContext: Option[String],
  promotedMetadata: Option[PromotedMetadata],
  groupedTrends: Option[Seq[GroupedTrend]])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = TrendItem.TrendItemEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
