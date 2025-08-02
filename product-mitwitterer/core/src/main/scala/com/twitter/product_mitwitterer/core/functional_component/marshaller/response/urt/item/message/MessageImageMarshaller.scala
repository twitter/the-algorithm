package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessageImage
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

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
