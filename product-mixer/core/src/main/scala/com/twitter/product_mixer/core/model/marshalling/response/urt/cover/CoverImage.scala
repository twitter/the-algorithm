package com.twitter.product_mixer.core.model.marshalling.response.urt.cover

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ImageAnimationType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ImageDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant

case class CoverImage(
  imageVariant: ImageVariant,
  imageDisplayType: ImageDisplayType,
  imageAnimationType: Option[ImageAnimationType])
