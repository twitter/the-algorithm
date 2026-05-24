package com.twitter.product_mixer.core.model.marshalling.response.urt.richtext

sealed trait RichTextFormat {
  def name: String
}

case object Plain extends RichTextFormat {
  override val name: String = "Plain"
}

case object Strong extends RichTextFormat {
  override val name: String = "Strong"
}
