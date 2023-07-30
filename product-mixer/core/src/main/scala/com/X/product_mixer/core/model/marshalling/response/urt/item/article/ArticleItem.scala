package com.X.product_mixer.core.model.marshalling.response.urt.item.article

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.X.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem

object ArticleItem {
  val ArticleEntryNamespace = EntryNamespace("article")
}

case class ArticleItem(
  override val id: Int,
  articleSeedType: ArticleSeedType,
  override val sortIndex: Option[Long],
  override val clientEventInfo: Option[ClientEventInfo],
  override val feedbackActionInfo: Option[FeedbackActionInfo],
  displayType: Option[ArticleDisplayType],
  socialContext: Option[SocialContext])
    extends TimelineItem {
  override val entryNamespace: EntryNamespace = ArticleItem.ArticleEntryNamespace

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
