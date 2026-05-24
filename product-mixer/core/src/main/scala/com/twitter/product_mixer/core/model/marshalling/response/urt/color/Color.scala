package com.twitter.product_mixer.core.model.marshalling.response.urt.color

case class Color(
  red: Short,
  green: Short,
  blue: Short,
  opacity: Option[Short])
