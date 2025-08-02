package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.ReferenceObject
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertCashtag
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertHashtag
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertList
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertMention
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertUser
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ReferenceObjectMarshaller @Inject() (urlMarshaller: UrlMarshaller) {

  def apply(ref: ReferenceObject): urt.ReferenceObject = ref match {
    case url: Url => urt.ReferenceObject.Url(urlMarshaller(url))
    case user: RichTetwittertUser => urt.ReferenceObject.User(urt.RichTetwittertUser(id = user.id))
    case mention: RichTetwittertMention =>
      urt.ReferenceObject.Mention(
        urt.RichTetwittertMention(id = mention.id, screenName = mention.screenName))
    case hashtag: RichTetwittertHashtag =>
      urt.ReferenceObject.Hashtag(urt.RichTetwittertHashtag(tetwittert = hashtag.tetwittert))
    case cashtag: RichTetwittertCashtag =>
      urt.ReferenceObject.Cashtag(urt.RichTetwittertCashtag(tetwittert = cashtag.tetwittert))
    case twitterList: RichTetwittertList =>
      urt.ReferenceObject.TwitterList(urt.RichTetwittertList(id = twitterList.id, url = twitterList.url))
  }
}
