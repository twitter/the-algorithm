package com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem

object TopicFollowPromptItem {
  val TopicFollowPromptEntryNamespace = EntryNamespace("topicfollowprompt")
}

case class TopicFollowPromptItem(
  override val id: Long,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  topicFollowPromptDisplayType: TopicFollowPromptDisplayType,
  followIncentiveTitle: Option[String],
  followIncentiveText: Option[String])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace =
    TopicFollowPromptItem.TopicFollowPromptEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
