package com.twitter.product_mixer.core.model.marshalling.response.urt.alert

import com.twitter.product_mixer.core.model.marshalling.response.urt.color.RosettaColor

case class ShowAlertColorConfiguration(
  background: RosettaColor,
  text: RosettaColor,
  border: Option[RosettaColor],
)
