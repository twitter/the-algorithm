package com.twitter.home_mixer.util

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.DeepLink
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ExternalUrl
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.UrtEndpoint
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.UrtEndpointOptions
import com.twitter.timelines.render.{thriftscala => t}

object UrtUtil {
  def transformUrl(url: t.Url): Url = {
    val endpointOptions = url.urtEndpointOptions.map { options =>
      UrtEndpointOptions(
        requestParams = options.requestParams.map(_.toMap),
        title = options.title,
        cacheId = options.cacheId,
        subtitle = options.subtitle
      )
    }

    val urlType = url.urlType match {
      case t.UrlType.ExternalUrl => ExternalUrl
      case t.UrlType.DeepLink => DeepLink
      case t.UrlType.UrtEndpoint => UrtEndpoint
      case t.UrlType.EnumUnknownUrlType(field) =>
        throw new UnknownUrlTypeException(field)
    }

    Url(urlType = urlType, url = url.url, urtEndpointOptions = endpointOptions)
  }
}
class UnknownUrlTypeException(field: Int)
    extends UnsupportedOperationException(s"Unknown url type: $field")
