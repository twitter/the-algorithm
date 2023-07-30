package com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ImageDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Icon
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.FullWidth
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.IconSmall
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDisplayTypeMarshaller @Inject() () {

  def apply(imageDisplayType: ImageDisplayType): urt.ImageDisplayType =
    imageDisplayType match {
      case Icon => urt.ImageDisplayType.Icon
      case FullWidth => urt.ImageDisplayType.FullWidth
      case IconSmall => urt.ImageDisplayType.IconSmall
    }
}
