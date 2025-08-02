package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.ReferenceObject

sealed trait UrlType
case object EtwitterternalUrl etwittertends UrlType
case object DeepLink etwittertends UrlType
case object UrtEndpoint etwittertends UrlType

case class UrtEndpointOptions(
  requestParams: Option[Map[String, String]],
  title: Option[String],
  cacheId: Option[String],
  subtitle: Option[String])

case class Url(urlType: UrlType, url: String, urtEndpointOptions: Option[UrtEndpointOptions] = None)
    etwittertends ReferenceObject
