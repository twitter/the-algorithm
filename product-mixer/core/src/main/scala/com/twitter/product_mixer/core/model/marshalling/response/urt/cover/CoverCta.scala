package com.twitter.product_mixer.core.model.marshalling.response.urt.cover

import com.twitter.product_mixer.core.model.marshalling.response.urt.button.ButtonStyle
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo

case class CoverCta(
  text: String,
  ctaBehavior: CoverCtaBehavior,
  callbacks: Option[List[Callback]],
  clientEventInfo: Option[ClientEventInfo],
  icon: Option[HorizonIcon],
  buttonStyle: Option[ButtonStyle])
