package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.AdsMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class AdsMetadataMarshaller @Inject() () {

  def apply(adsMetadata: AdsMetadata): urt.AdsMetadata =
    urt.AdsMetadata(carouselId = adsMetadata.carouselId)
}
