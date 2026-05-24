package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.CallToAction
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Singleton

@Singleton
class CallToActionMarshaller {
  def apply(callToAction: CallToAction): urt.CallToAction = {
    urt.CallToAction(
      callToActionType = callToAction.callToActionType,
      url = callToAction.url
    )
  }
}
