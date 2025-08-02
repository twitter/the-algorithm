package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.media

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.media.Media
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class MediaMarshaller @Inject() (
  mediaEntityMarshaller: MediaEntityMarshaller,
  mediaKeyMarshaller: MediaKeyMarshaller,
  rectMarshaller: RectMarshaller,
  aspectRatioMarshaller: AspectRatioMarshaller) {

  def apply(media: Media): urt.Media = urt.Media(
    mediaEntity = media.mediaEntity.map(mediaEntityMarshaller(_)),
    mediaKey = media.mediaKey.map(mediaKeyMarshaller(_)),
    imagePossibleCropping = media.imagePossibleCropping.map { rects =>
      rects.map(rectMarshaller(_))
    },
    aspectRatio = media.aspectRatio.map(aspectRatioMarshaller(_))
  )
}
