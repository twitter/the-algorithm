package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.MediaInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaInfoMarshaller @Inject() (
  callToActionMarshaller: CallToActionMarshaller,
  videoVariantsMarshaller: VideoVariantsMarshaller) {
  def apply(mediaInfo: MediaInfo): urt.MediaInfo = {
    urt.MediaInfo(
      uuid = mediaInfo.uuid,
      publisherId = mediaInfo.publisherId,
      callToAction = mediaInfo.callToAction.map(callToActionMarshaller(_)),
      durationMillis = mediaInfo.durationMillis,
      videoVariants = mediaInfo.videoVariants.map(videoVariantsMarshaller(_)),
      advertiserName = mediaInfo.advertiserName,
      renderAdByAdvertiserName = mediaInfo.renderAdByAdvertiserName,
      advertiserProfileImageUrl = mediaInfo.advertiserProfileImageUrl
    )
  }
}
