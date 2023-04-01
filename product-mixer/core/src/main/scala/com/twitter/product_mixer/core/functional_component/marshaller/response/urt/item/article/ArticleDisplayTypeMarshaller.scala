package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.article

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.article.ArticleDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.article.Default
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDisplayTypeMarshaller @Inject() () {
  def apply(articleDisplayType: ArticleDisplayType): urt.ArticleDisplayType =
    articleDisplayType match {
      case Default => urt.ArticleDisplayType.Default
    }
}
