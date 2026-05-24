package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

case class PrerollMetadata(
  preroll: Option[Preroll],
  videoAnalyticsScribePassthrough: Option[String])
