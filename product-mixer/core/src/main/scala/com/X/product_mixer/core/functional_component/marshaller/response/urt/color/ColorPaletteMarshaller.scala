package com.X.product_mixer.core.functional_component.marshaller.response.urt.color

import com.X.product_mixer.core.model.marshalling.response.urt.color.ColorPalette
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorPaletteMarshaller @Inject() (
  colorMarshaller: ColorMarshaller) {

  def apply(colorPalette: ColorPalette): urt.ColorPaletteItem = urt.ColorPaletteItem(
    rgb = colorMarshaller(colorPalette.rgb),
    percentage = colorPalette.percentage
  )
}
