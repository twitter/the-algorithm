package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.Cover
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.FullCover.FullCoverEntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.HalfCover.HalfCoverEntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo

object HalfCover {
  val HalfCoverEntryNamespace = EntryNamespace("half-cover")
}
case class HalfCover(
  override val id: String,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  content: HalfCoverContent)
    etwittertends Cover {

  override val entryNamespace: EntryNamespace = HalfCoverEntryNamespace

  // Note that sort indetwitter is not used for Covers, as they are not TimelineEntry and do not have entryId
  override def withSortIndetwitter(newSortIndetwitter: Long): TimelineEntry =
    copy(sortIndetwitter = Some(newSortIndetwitter))

  // Not used for covers
  override def feedbackActionInfo: Option[FeedbackActionInfo] = None
}

object FullCover {
  val FullCoverEntryNamespace = EntryNamespace("full-cover")
}
case class FullCover(
  override val id: String,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  content: FullCoverContent)
    etwittertends Cover {

  override val entryNamespace: EntryNamespace = FullCoverEntryNamespace

  // Note that sort indetwitter is not used for Covers, as they are not TimelineEntry and do not have entryId
  override def withSortIndetwitter(newSortIndetwitter: Long): TimelineEntry =
    copy(sortIndetwitter = Some(newSortIndetwitter))

  // Not used for covers
  override def feedbackActionInfo: Option[FeedbackActionInfo] = None
}
