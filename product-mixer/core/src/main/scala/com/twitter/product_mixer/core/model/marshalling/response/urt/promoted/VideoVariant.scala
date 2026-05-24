package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

case class VideoVariant(
  url: Option[String],
  contentType: Option[String],
  bitrate: Option[Int])
