package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.DcmUrlOverrideType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.UnknownUrlOverrideType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.UrlOverrideType
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class UrlOverrideTypeMarshaller @Inject() () {

  def apply(urlOverrideType: UrlOverrideType): urt.UrlOverrideType = urlOverrideType match {
    case UnknownUrlOverrideType => urt.UrlOverrideType.Unknown
    case DcmUrlOverrideType => urt.UrlOverrideType.Dcm
  }
}
