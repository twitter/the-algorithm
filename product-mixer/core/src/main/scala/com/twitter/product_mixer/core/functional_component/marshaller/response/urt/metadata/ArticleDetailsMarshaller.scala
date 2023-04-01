package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ArticleDetails
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDetailsMarshaller @Inject() () {

  def apply(articleDetails: ArticleDetails): urt.ArticleDetails = urt.ArticleDetails(
    articlePosition = articleDetails.articlePosition,
    shareCount = articleDetails.shareCount
  )
}
