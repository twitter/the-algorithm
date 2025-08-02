package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.color.ColorPaletteMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ImageVariantMarshaller @Inject() (
  colorPaletteMarshaller: ColorPaletteMarshaller) {

  def apply(imageVariant: ImageVariant): urt.ImageVariant = urt.ImageVariant(
    url = imageVariant.url,
    width = imageVariant.width,
    height = imageVariant.height,
    palette = imageVariant.palette.map { paletteList => paletteList.map(colorPaletteMarshaller(_)) }
  )
}
