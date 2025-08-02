package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.article

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem

object ArticleItem {
  val ArticleEntryNamespace = EntryNamespace("article")
}

case class ArticleItem(
  override val id: Int,
  articleSeedType: ArticleSeedType,
  override val sortIndetwitter: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  displayType: Option[ArticleDisplayType],
  socialContetwittert: Option[SocialContetwittert])
    etwittertends TimelineItem {
  override val entryNamespace: EntryNamespace = ArticleItem.ArticleEntryNamespace

  override def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry = copy(sortIndetwitter = Some(sortIndetwitter))
}
