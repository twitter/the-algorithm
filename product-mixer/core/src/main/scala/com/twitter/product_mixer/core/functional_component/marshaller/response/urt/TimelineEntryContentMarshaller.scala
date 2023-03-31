package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineOperation
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineEntryContentMarshaller @Inject() (
  timelineItemMarshaller: TimelineItemMarshaller,
  timelineModuleMarshaller: TimelineModuleMarshaller,
  timelineOperationMarshaller: TimelineOperationMarshaller) {

  def apply(entry: TimelineEntry): urt.TimelineEntryContent = entry match {
    case item: TimelineItem =>
      urt.TimelineEntryContent.Item(timelineItemMarshaller(item))
    case module: TimelineModule =>
      urt.TimelineEntryContent.TimelineModule(timelineModuleMarshaller(module))
    case operation: TimelineOperation =>
      urt.TimelineEntryContent.Operation(timelineOperationMarshaller(operation))
  }
}
