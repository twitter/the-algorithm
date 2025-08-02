package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.cover

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ImageAnimationTypeMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ImageDisplayTypeMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverImage
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CoverImageMarshaller @Inject() (
  imageVariantMarshaller: ImageVariantMarshaller,
  imageDisplayTypeMarshaller: ImageDisplayTypeMarshaller,
  imageAnimationTypeMarshaller: ImageAnimationTypeMarshaller) {

  def apply(coverImage: CoverImage): urt.CoverImage =
    urt.CoverImage(
      image = imageVariantMarshaller(coverImage.imageVariant),
      imageDisplayType = imageDisplayTypeMarshaller(coverImage.imageDisplayType),
      imageAnimationType = coverImage.imageAnimationType.map(imageAnimationTypeMarshaller(_))
    )
}
