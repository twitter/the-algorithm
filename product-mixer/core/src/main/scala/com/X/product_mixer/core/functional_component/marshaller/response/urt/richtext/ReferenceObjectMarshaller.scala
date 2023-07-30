package com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.ReferenceObject
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextCashtag
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextHashtag
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextList
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextMention
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextUser
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReferenceObjectMarshaller @Inject() (urlMarshaller: UrlMarshaller) {

  def apply(ref: ReferenceObject): urt.ReferenceObject = ref match {
    case url: Url => urt.ReferenceObject.Url(urlMarshaller(url))
    case user: RichTextUser => urt.ReferenceObject.User(urt.RichTextUser(id = user.id))
    case mention: RichTextMention =>
      urt.ReferenceObject.Mention(
        urt.RichTextMention(id = mention.id, screenName = mention.screenName))
    case hashtag: RichTextHashtag =>
      urt.ReferenceObject.Hashtag(urt.RichTextHashtag(text = hashtag.text))
    case cashtag: RichTextCashtag =>
      urt.ReferenceObject.Cashtag(urt.RichTextCashtag(text = cashtag.text))
    case XList: RichTextList =>
      urt.ReferenceObject.XList(urt.RichTextList(id = XList.id, url = XList.url))
  }
}
