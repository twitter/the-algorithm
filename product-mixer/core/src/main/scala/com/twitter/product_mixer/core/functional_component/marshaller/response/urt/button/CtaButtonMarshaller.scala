package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.button

import com.twitter.product_mixer.core.model.marshalling.response.urt.button.CtaButton
import com.twitter.product_mixer.core.model.marshalling.response.urt.button.IconCtaButton
import com.twitter.product_mixer.core.model.marshalling.response.urt.button.TextCtaButton
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CtaButtonMarshaller @Inject() (
  iconCtaButtonMarshaller: IconCtaButtonMarshaller,
  textCtaButtonMarshaller: TextCtaButtonMarshaller) {

  def apply(ctaButton: CtaButton): urt.CtaButton = ctaButton match {
    case button: TextCtaButton => urt.CtaButton.Text(textCtaButtonMarshaller(button))
    case button: IconCtaButton => urt.CtaButton.Icon(iconCtaButtonMarshaller(button))
  }
}
