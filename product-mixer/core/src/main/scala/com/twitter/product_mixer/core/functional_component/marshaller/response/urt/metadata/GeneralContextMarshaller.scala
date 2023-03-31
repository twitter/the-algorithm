package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.GeneralContext
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeneralContextMarshaller @Inject() (
  generalContextTypeMarshaller: GeneralContextTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(generalContext: GeneralContext): urt.SocialContext = {
    urt.SocialContext.GeneralContext(
      urt.GeneralContext(
        contextType = generalContextTypeMarshaller(generalContext.contextType),
        text = generalContext.text,
        url = generalContext.url,
        contextImageUrls = generalContext.contextImageUrls,
        landingUrl = generalContext.landingUrl.map(urlMarshaller(_))
      )
    )
  }
}
