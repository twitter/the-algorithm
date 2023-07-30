package com.X.product_mixer.core.model.marshalling.response.urt.item.tombstone

import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichText

case class TombstoneInfo(
  text: String,
  richText: Option[RichText],
  richRevealText: Option[RichText])
