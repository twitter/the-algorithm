package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

import scala.collection.Map

case class ClickTrackingInfo(
  urlParams: Map[String, String],
  urlOverride: Option[String],
  urlOverrideType: Option[UrlOverrideType])
