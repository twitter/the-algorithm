package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.PromotedMetadata

object UserItem {
  val UserEntryNamespace: EntryNamespace = EntryNamespace("user")
}

case class UserItem(
  override val id: Long,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  override val isMarkUnread: Option[Boolean],
  displayType: UserDisplayType,
  promotedMetadata: Option[PromotedMetadata],
  socialContetwittert: Option[SocialContetwittert],
  reactiveTriggers: Option[UserReactiveTriggers],
  enableReactiveBlending: Option[Boolean])
    etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace = UserItem.UserEntryNamespace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
