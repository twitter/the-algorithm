package com.twitter.product_mixer.core.model.marshalling.response.urt.richtext

case class RichTextEntity(
  fromIndex: Int,
  toIndex: Int,
  ref: Option[ReferenceObject],
  format: Option[RichTextFormat])
