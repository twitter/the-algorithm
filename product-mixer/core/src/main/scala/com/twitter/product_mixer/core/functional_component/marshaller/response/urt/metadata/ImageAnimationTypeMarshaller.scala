package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ImageAnimationType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Bounce
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageAnimationTypeMarshaller @Inject() () {

  def apply(imageAnimationType: ImageAnimationType): urt.ImageAnimationType =
    imageAnimationType match {
      case Bounce => urt.ImageAnimationType.Bounce
    }
}
