package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url

sealed trait CtaButton

case class TetwittertCtaButton(buttonTetwittert: String, url: Url) etwittertends CtaButton

case class IconCtaButton(buttonIcon: HorizonIcon, accessibilityLabel: String, url: Url)
    etwittertends CtaButton
