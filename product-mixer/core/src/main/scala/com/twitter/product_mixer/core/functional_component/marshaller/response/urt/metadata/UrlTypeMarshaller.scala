package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.DeepLink
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ExternalUrl
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.UrlType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.UrtEndpoint
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrlTypeMarshaller @Inject() () {

  def apply(urlType: UrlType): urt.UrlType = urlType match {
    case ExternalUrl => urt.UrlType.ExternalUrl
    case DeepLink => urt.UrlType.DeepLink
    case UrtEndpoint => urt.UrlType.UrtEndpoint
  }
}
