package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineMetadata
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TimelineMetadataMarshaller @Inject() (
  timelineScribeConfigMarshaller: TimelineScribeConfigMarshaller,
  readerModeConfigMarshaller: ReaderModeConfigMarshaller) {

  def apply(timelineMetadata: TimelineMetadata): urt.TimelineMetadata = urt.TimelineMetadata(
    title = timelineMetadata.title,
    scribeConfig = timelineMetadata.scribeConfig.map(timelineScribeConfigMarshaller(_)),
    readerModeConfig = timelineMetadata.readerModeConfig.map(readerModeConfigMarshaller(_))
  )
}
