package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tombstone

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert

case class TombstoneInfo(
  tetwittert: String,
  richTetwittert: Option[RichTetwittert],
  richRevealTetwittert: Option[RichTetwittert])
