package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.button

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.TetwittertCtaButton
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TetwittertCtaButtonMarshaller @Inject() (
  urlMarshaller: UrlMarshaller) {

  def apply(tetwittertCtaButton: TetwittertCtaButton): urt.TetwittertCtaButton =
    urt.TetwittertCtaButton(
      buttonTetwittert = tetwittertCtaButton.buttonTetwittert,
      url = urlMarshaller(tetwittertCtaButton.url)
    )
}
