package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.AdsMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdsMetadataMarshaller @Inject() () {

  def apply(adsMetadata: AdsMetadata): urt.AdsMetadata =
    urt.AdsMetadata(carouselId = adsMetadata.carouselId)
}
