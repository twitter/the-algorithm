package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.ClickTrackingInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClickTrackingInfoMarshaller @Inject() (
  urlOverrideTypeMarshaller: UrlOverrideTypeMarshaller) {

  def apply(clickTrackingInfo: ClickTrackingInfo): urt.ClickTrackingInfo =
    urt.ClickTrackingInfo(
      urlParams = clickTrackingInfo.urlParams,
      urlOverride = clickTrackingInfo.urlOverride,
      urlOverrideType = clickTrackingInfo.urlOverrideType.map(urlOverrideTypeMarshaller(_))
    )
}
