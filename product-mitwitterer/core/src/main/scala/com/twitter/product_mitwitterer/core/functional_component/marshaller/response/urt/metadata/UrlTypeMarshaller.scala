package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.DeepLink
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.EtwitterternalUrl
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.UrlType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.UrtEndpoint
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class UrlTypeMarshaller @Inject() () {

  def apply(urlType: UrlType): urt.UrlType = urlType match {
    case EtwitterternalUrl => urt.UrlType.EtwitterternalUrl
    case DeepLink => urt.UrlType.DeepLink
    case UrtEndpoint => urt.UrlType.UrtEndpoint
  }
}
