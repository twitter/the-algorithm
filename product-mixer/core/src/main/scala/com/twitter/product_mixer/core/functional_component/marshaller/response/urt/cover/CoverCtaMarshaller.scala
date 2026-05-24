package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.cover

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.button.ButtonStyleMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.cover.CoverCta
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoverCtaMarshaller @Inject() (
  coverCtaBehaviorMarshaller: CoverCtaBehaviorMarshaller,
  callbackMarshaller: CallbackMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller,
  horizonIconMarshaller: HorizonIconMarshaller,
  buttonStyleMarshaller: ButtonStyleMarshaller) {

  def apply(coverCta: CoverCta): urt.CoverCta = urt.CoverCta(
    text = coverCta.text,
    ctaBehavior = coverCtaBehaviorMarshaller(coverCta.ctaBehavior),
    callbacks = coverCta.callbacks.map(_.map(callbackMarshaller(_))),
    clientEventInfo = coverCta.clientEventInfo.map(clientEventInfoMarshaller(_)),
    icon = coverCta.icon.map(horizonIconMarshaller(_)),
    buttonStyle = coverCta.buttonStyle.map(buttonStyleMarshaller(_))
  )
}
