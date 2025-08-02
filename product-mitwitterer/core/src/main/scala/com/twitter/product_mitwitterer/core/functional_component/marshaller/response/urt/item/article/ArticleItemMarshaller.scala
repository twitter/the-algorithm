package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.article

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.SocialContetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.article.ArticleItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ArticleItemMarshaller @Inject() (
  articleDisplayTypeMarshaller: ArticleDisplayTypeMarshaller,
  socialContetwittertMarshaller: SocialContetwittertMarshaller,
  articleSeedTypeMarshaller: ArticleSeedTypeMarshaller) {
  def apply(articleItem: ArticleItem): urt.TimelineItemContent =
    urt.TimelineItemContent.Article(
      urt.Article(
        id = articleItem.id,
        displayType = articleItem.displayType.map(articleDisplayTypeMarshaller(_)),
        socialContetwittert = articleItem.socialContetwittert.map(socialContetwittertMarshaller(_)),
        articleSeedType = Some(articleSeedTypeMarshaller(articleItem.articleSeedType))
      )
    )
}
