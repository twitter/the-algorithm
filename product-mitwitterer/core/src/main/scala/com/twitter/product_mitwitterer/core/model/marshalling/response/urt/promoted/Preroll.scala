package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted

case class Preroll(
  prerollId: Option[String],
  dynamicPrerollType: Option[DynamicPrerollType],
  mediaInfo: Option[MediaInfo])
