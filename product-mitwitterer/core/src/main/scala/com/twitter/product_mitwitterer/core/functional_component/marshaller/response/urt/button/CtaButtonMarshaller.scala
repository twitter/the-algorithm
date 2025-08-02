package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.button

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.CtaButton
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.IconCtaButton
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.TetwittertCtaButton
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CtaButtonMarshaller @Inject() (
  iconCtaButtonMarshaller: IconCtaButtonMarshaller,
  tetwittertCtaButtonMarshaller: TetwittertCtaButtonMarshaller) {

  def apply(ctaButton: CtaButton): urt.CtaButton = ctaButton match {
    case button: TetwittertCtaButton => urt.CtaButton.Tetwittert(tetwittertCtaButtonMarshaller(button))
    case button: IconCtaButton => urt.CtaButton.Icon(iconCtaButtonMarshaller(button))
  }
}
