package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.media

import com.twitter.product_mixer.core.model.marshalling.response.urt.media.Rect
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class RectMarshaller @Inject() () {

  def apply(rect: Rect): urt.Rect = urt.Rect(
    left = rect.left,
    top = rect.top,
    width = rect.width,
    height = rect.height
  )
}
