package com.twitter.product_mixer.core.model.marshalling.response.urt.item.tile

import com.twitter.product_mixer.core.model.marshalling.response.urt.button.CtaButton
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Badge
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText

sealed trait TileContent

case class StandardTileContent(
  title: String,
  supportingText: String,
  badge: Option[Badge])
    extends TileContent

case class CallToActionTileContent(
  text: String,
  richText: Option[RichText],
  ctaButton: Option[CtaButton])
    extends TileContent

//todo: Add other TileContent types later
