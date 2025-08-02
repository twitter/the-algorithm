package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.color

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.ColorPalette
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ColorPaletteMarshaller @Inject() (
  colorMarshaller: ColorMarshaller) {

  def apply(colorPalette: ColorPalette): urt.ColorPaletteItem = urt.ColorPaletteItem(
    rgb = colorMarshaller(colorPalette.rgb),
    percentage = colorPalette.percentage
  )
}
