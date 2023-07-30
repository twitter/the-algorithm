package com.X.product_mixer.core.model.marshalling.response.urt.cover

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ImageAnimationType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ImageDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant

case class CoverImage(
  imageVariant: ImageVariant,
  imageDisplayType: ImageDisplayType,
  imageAnimationType: Option[ImageAnimationType])
