package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.button

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.button.IconCtaButton
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IconCtaButtonMarshaller @Inject() (
  horizonIconMarshaller: HorizonIconMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(iconCtaButton: IconCtaButton): urt.IconCtaButton =
    urt.IconCtaButton(
      buttonIcon = horizonIconMarshaller(iconCtaButton.buttonIcon),
      accessibilityLabel = iconCtaButton.accessibilityLabel,
      url = urlMarshaller(iconCtaButton.url)
    )
}
