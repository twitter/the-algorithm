package com.twitter.product_mixer.core.model.marshalling.response.urt.richtext

sealed trait RichTextAlignment

case object Natural extends RichTextAlignment
case object Center extends RichTextAlignment
