package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Icon
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FullWidth
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.IconSmall
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ImageDisplayTypeMarshaller @Inject() () {

  def apply(imageDisplayType: ImageDisplayType): urt.ImageDisplayType =
    imageDisplayType match {
      case Icon => urt.ImageDisplayType.Icon
      case FullWidth => urt.ImageDisplayType.FullWidth
      case IconSmall => urt.ImageDisplayType.IconSmall
    }
}
