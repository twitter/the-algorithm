package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.button

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.ButtonStyle
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.Default
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.Primary
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.Secondary
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.Tetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.Destructive
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.Neutral
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.DestructiveSecondary
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.DestructiveTetwittert
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ButtonStyleMarshaller @Inject() () {
  def apply(buttonStyle: ButtonStyle): urt.ButtonStyle =
    buttonStyle match {
      case Default => urt.ButtonStyle.Default
      case Primary => urt.ButtonStyle.Primary
      case Secondary => urt.ButtonStyle.Secondary
      case Tetwittert => urt.ButtonStyle.Tetwittert
      case Destructive => urt.ButtonStyle.Destructive
      case Neutral => urt.ButtonStyle.Neutral
      case DestructiveSecondary => urt.ButtonStyle.DestructiveSecondary
      case DestructiveTetwittert => urt.ButtonStyle.DestructiveTetwittert
    }
}
