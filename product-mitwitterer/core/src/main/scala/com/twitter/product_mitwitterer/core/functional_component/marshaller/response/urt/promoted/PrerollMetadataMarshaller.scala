package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.PrerollMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class PrerollMetadataMarshaller @Inject() (
  prerollMarshaller: PrerollMarshaller) {
  def apply(prerollMetadata: PrerollMetadata): urt.PrerollMetadata =
    urt.PrerollMetadata(
      preroll = prerollMetadata.preroll.map(prerollMarshaller(_)),
      videoAnalyticsScribePassthrough = prerollMetadata.videoAnalyticsScribePassthrough
    )
}
