package com.twitter.product_mixer.core.model.marshalling.response.urt

case class TimelineScribeConfig(
  page: Option[String],
  section: Option[String],
  entityToken: Option[String])
