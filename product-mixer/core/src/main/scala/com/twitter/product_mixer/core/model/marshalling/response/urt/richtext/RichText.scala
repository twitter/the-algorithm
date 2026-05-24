package com.twitter.product_mixer.core.model.marshalling.response.urt.richtext

case class RichText(
  text: String,
  entities: List[RichTextEntity],
  rtl: Option[Boolean],
  alignment: Option[RichTextAlignment])
