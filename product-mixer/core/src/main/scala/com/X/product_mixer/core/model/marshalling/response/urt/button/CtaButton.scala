package com.X.product_mixer.core.model.marshalling.response.urt.button

import com.X.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Url

sealed trait CtaButton

case class TextCtaButton(buttonText: String, url: Url) extends CtaButton

case class IconCtaButton(buttonIcon: HorizonIcon, accessibilityLabel: String, url: Url)
    extends CtaButton
