package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.media

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.media.AspectRatio
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class AspectRatioMarshaller @Inject() () {

  def apply(aspectRatio: AspectRatio): urt.AspectRatio = urt.AspectRatio(
    numerator = aspectRatio.numerator,
    denominator = aspectRatio.denominator
  )
}
