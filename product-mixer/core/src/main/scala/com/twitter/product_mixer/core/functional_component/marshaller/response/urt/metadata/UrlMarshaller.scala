package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrlMarshaller @Inject() (
  urlTypeMarshaller: UrlTypeMarshaller,
  urtEndpointOptionsMarshaller: UrtEndpointOptionsMarshaller) {

  def apply(url: Url): urt.Url = urt.Url(
    urlType = urlTypeMarshaller(url.urlType),
    url = url.url,
    urtEndpointOptions = url.urtEndpointOptions.map(urtEndpointOptionsMarshaller(_))
  )
}
