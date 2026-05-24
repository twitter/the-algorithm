package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.MessageImage
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageImageMarshaller @Inject() (
  imageVariantMarshaller: ImageVariantMarshaller) {

  def apply(messageImage: MessageImage): urt.MessageImage = {
    urt.MessageImage(
      imageVariants = messageImage.imageVariants.map(imageVariantMarshaller(_)),
      backgroundColor = messageImage.backgroundColor
    )
  }
}
