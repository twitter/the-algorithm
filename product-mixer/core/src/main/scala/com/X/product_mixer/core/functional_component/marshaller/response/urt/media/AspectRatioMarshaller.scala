package com.X.product_mixer.core.functional_component.marshaller.response.urt.media

import com.X.product_mixer.core.model.marshalling.response.urt.media.AspectRatio
import javax.inject.Inject
import javax.inject.Singleton
import com.X.timelines.render.{thriftscala => urt}

@Singleton
class AspectRatioMarshaller @Inject() () {

  def apply(aspectRatio: AspectRatio): urt.AspectRatio = urt.AspectRatio(
    numerator = aspectRatio.numerator,
    denominator = aspectRatio.denominator
  )
}
