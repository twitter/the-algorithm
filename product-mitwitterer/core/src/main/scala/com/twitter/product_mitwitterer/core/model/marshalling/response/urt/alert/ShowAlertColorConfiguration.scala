package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.alert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.RosettaColor

case class ShowAlertColorConfiguration(
  background: RosettaColor,
  tetwittert: RosettaColor,
  border: Option[RosettaColor],
)
