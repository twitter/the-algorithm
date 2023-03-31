package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

case class MediaInfo(
  uuid: Option[String],
  publisherId: Option[Long],
  callToAction: Option[CallToAction],
  durationMillis: Option[Int],
  videoVariants: Option[Seq[VideoVariant]],
  advertiserName: Option[String],
  renderAdByAdvertiserName: Option[Boolean],
  advertiserProfileImageUrl: Option[String])
