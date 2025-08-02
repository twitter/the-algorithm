package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.button.CtaButton
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Badge
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert

sealed trait TileContent

case class StandardTileContent(
  title: String,
  supportingTetwittert: String,
  badge: Option[Badge])
    etwittertends TileContent

case class CallToActionTileContent(
  tetwittert: String,
  richTetwittert: Option[RichTetwittert],
  ctaButton: Option[CtaButton])
    etwittertends TileContent

//todo: Add other TileContent types later
