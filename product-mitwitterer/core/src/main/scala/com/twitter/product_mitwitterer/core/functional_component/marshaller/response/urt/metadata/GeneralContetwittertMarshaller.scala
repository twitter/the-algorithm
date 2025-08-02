package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittert
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class GeneralContetwittertMarshaller @Inject() (
  generalContetwittertTypeMarshaller: GeneralContetwittertTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(generalContetwittert: GeneralContetwittert): urt.SocialContetwittert = {
    urt.SocialContetwittert.GeneralContetwittert(
      urt.GeneralContetwittert(
        contetwittertType = generalContetwittertTypeMarshaller(generalContetwittert.contetwittertType),
        tetwittert = generalContetwittert.tetwittert,
        url = generalContetwittert.url,
        contetwittertImageUrls = generalContetwittert.contetwittertImageUrls,
        landingUrl = generalContetwittert.landingUrl.map(urlMarshaller(_))
      )
    )
  }
}
