package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.ReferenceObject

sealed trait UrlType
case object ExternalUrl extends UrlType
case object DeepLink extends UrlType
case object UrtEndpoint extends UrlType

case class UrtEndpointOptions(
  requestParams: Option[Map[String, String]],
  title: Option[String],
  cacheId: Option[String],
  subtitle: Option[String])

case class Url(urlType: UrlType, url: String, urtEndpointOptions: Option[UrtEndpointOptions] = None)
    extends ReferenceObject
