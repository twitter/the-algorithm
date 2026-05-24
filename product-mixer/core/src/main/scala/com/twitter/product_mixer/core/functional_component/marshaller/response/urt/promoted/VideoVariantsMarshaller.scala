package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.VideoVariant
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Singleton

@Singleton
class VideoVariantsMarshaller {
  def apply(videoVariants: Seq[VideoVariant]): Seq[urt.VideoVariant] = {
    videoVariants.map(videoVariant =>
      urt.VideoVariant(
        url = videoVariant.url,
        contentType = videoVariant.contentType,
        bitrate = videoVariant.bitrate
      ))
  }
}
