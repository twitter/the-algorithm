package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.CallToAction
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Singleton

@Singleton
class CallToActionMarshaller {
  def apply(callToAction: CallToAction): urt.CallToAction = {
    urt.CallToAction(
      callToActionType = callToAction.callToActionType,
      url = callToAction.url
    )
  }
}
