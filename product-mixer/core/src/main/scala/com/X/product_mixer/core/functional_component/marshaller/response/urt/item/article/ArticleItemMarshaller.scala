package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.article

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.SocialContextMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.item.article.ArticleItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleItemMarshaller @Inject() (
  articleDisplayTypeMarshaller: ArticleDisplayTypeMarshaller,
  socialContextMarshaller: SocialContextMarshaller,
  articleSeedTypeMarshaller: ArticleSeedTypeMarshaller) {
  def apply(articleItem: ArticleItem): urt.TimelineItemContent =
    urt.TimelineItemContent.Article(
      urt.Article(
        id = articleItem.id,
        displayType = articleItem.displayType.map(articleDisplayTypeMarshaller(_)),
        socialContext = articleItem.socialContext.map(socialContextMarshaller(_)),
        articleSeedType = Some(articleSeedTypeMarshaller(articleItem.articleSeedType))
      )
    )
}
