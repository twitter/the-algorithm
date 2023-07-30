package com.X.product_mixer.core.functional_component.marshaller.response.urt.button

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.button.TextCtaButton
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextCtaButtonMarshaller @Inject() (
  urlMarshaller: UrlMarshaller) {

  def apply(textCtaButton: TextCtaButton): urt.TextCtaButton =
    urt.TextCtaButton(
      buttonText = textCtaButton.buttonText,
      url = urlMarshaller(textCtaButton.url)
    )
}
