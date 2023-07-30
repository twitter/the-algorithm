package com.X.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.X.product_mixer.core.model.marshalling.response.urt.promoted.PrerollMetadata
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrerollMetadataMarshaller @Inject() (
  prerollMarshaller: PrerollMarshaller) {
  def apply(prerollMetadata: PrerollMetadata): urt.PrerollMetadata =
    urt.PrerollMetadata(
      preroll = prerollMetadata.preroll.map(prerollMarshaller(_)),
      videoAnalyticsScribePassthrough = prerollMetadata.videoAnalyticsScribePassthrough
    )
}
