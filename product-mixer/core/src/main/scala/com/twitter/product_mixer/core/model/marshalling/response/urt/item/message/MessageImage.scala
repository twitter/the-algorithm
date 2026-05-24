package com.twitter.product_mixer.core.model.marshalling.response.urt.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant

case class MessageImage(
  imageVariants: Set[ImageVariant],
  backgroundColor: Option[String])
