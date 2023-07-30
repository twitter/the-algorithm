package com.X.product_mixer.core.model.marshalling.response.urt.cover

import com.X.product_mixer.core.model.marshalling.response.urt.button.ButtonStyle
import com.X.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Callback
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo

case class CoverCta(
  text: String,
  ctaBehavior: CoverCtaBehavior,
  callbacks: Option[List[Callback]],
  clientEventInfo: Option[ClientEventInfo],
  icon: Option[HorizonIcon],
  buttonStyle: Option[ButtonStyle])
