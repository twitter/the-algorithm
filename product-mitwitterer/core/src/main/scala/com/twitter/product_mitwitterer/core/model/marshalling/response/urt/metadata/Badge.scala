package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.RosettaColor

case class Badge(
  tetwittert: Option[String],
  tetwittertColorName: Option[RosettaColor],
  backgroundColorName: Option[RosettaColor])
