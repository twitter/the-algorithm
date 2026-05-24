package com.twitter.product_mixer.core.model.marshalling.response.urt.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo

case class MessageAction(
  dismissOnClick: Boolean,
  url: Option[String],
  clientEventInfo: Option[ClientEventInfo],
  onClickCallbacks: Option[Seq[Callback]])
