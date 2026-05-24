package com.twitter.product_mixer.core.model.marshalling.response.urt.item.moment

import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText

object MomentAnnotationItem {
  val MomentAnnotationEntryNamespace = EntryNamespace("momentannotation")
}

/**
 * Represents a MomentAnnotation URT item.
 * This is primarily used by Trends Searth Result Page for displaying Trends Title or Description
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/MomentAnnotation.html
 */
case class MomentAnnotationItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  override val isPinned: Option[Boolean],
  text: Option[RichText],
  header: Option[RichText],
) extends TimelineItem {

  override val entryNamespace: EntryNamespace =
    MomentAnnotationItem.MomentAnnotationEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
