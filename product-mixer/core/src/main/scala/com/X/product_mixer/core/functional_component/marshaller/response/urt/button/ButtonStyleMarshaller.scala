package com.X.product_mixer.core.functional_component.marshaller.response.urt.button

import com.X.product_mixer.core.model.marshalling.response.urt.button.ButtonStyle
import com.X.product_mixer.core.model.marshalling.response.urt.button.Default
import com.X.product_mixer.core.model.marshalling.response.urt.button.Primary
import com.X.product_mixer.core.model.marshalling.response.urt.button.Secondary
import com.X.product_mixer.core.model.marshalling.response.urt.button.Text
import com.X.product_mixer.core.model.marshalling.response.urt.button.Destructive
import com.X.product_mixer.core.model.marshalling.response.urt.button.Neutral
import com.X.product_mixer.core.model.marshalling.response.urt.button.DestructiveSecondary
import com.X.product_mixer.core.model.marshalling.response.urt.button.DestructiveText
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ButtonStyleMarshaller @Inject() () {
  def apply(buttonStyle: ButtonStyle): urt.ButtonStyle =
    buttonStyle match {
      case Default => urt.ButtonStyle.Default
      case Primary => urt.ButtonStyle.Primary
      case Secondary => urt.ButtonStyle.Secondary
      case Text => urt.ButtonStyle.Text
      case Destructive => urt.ButtonStyle.Destructive
      case Neutral => urt.ButtonStyle.Neutral
      case DestructiveSecondary => urt.ButtonStyle.DestructiveSecondary
      case DestructiveText => urt.ButtonStyle.DestructiveText
    }
}
