package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet_composer

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem

object TweetComposerItem {
  val TweetComposerEntryNameSpace = EntryNamespace("tweetcomposer")
}

case class TweetComposerItem(
  override val id: Long,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  displayType: TweetComposerDisplayType,
  tetwittert: String,
  url: Url)
    etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace = TweetComposerItem.TweetComposerEntryNameSpace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
