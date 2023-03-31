package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.color.ColorPalette

case class ImageVariant(
  url: String,
  width: Int,
  height: Int,
  palette: Option[List[ColorPalette]])
