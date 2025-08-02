package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.color

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.Color
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Singleton

@Singleton
class ColorMarshaller {

  def apply(color: Color): urt.Color = urt.Color(
    red = color.red,
    green = color.green,
    blue = color.blue,
    opacity = color.opacity
  )
}
