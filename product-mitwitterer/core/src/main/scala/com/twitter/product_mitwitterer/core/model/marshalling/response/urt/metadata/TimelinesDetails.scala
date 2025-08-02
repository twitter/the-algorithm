package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata

case class TimelinesDetails(
  injectionType: Option[String],
  controllerData: Option[String],
  sourceData: Option[String])
