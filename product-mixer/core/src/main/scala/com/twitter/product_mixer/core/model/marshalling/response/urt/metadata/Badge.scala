package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.color.RosettaColor

case class Badge(
  text: Option[String],
  textColorName: Option[RosettaColor],
  backgroundColorName: Option[RosettaColor])
