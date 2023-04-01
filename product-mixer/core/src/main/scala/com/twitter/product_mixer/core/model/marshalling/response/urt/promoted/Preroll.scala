package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

case class Preroll(
  prerollId: Option[String],
  dynamicPrerollType: Option[DynamicPrerollType],
  mediaInfo: Option[MediaInfo])
