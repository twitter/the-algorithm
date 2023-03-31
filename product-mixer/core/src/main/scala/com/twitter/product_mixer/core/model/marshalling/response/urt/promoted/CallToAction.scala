package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

case class CallToAction(
  callToActionType: Option[String],
  url: Option[String])
