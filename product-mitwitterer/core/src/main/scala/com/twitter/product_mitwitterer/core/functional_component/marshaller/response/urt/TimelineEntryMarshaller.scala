package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TimelineEntryMarshaller @Inject() (
  timelineEntryContentMarshaller: TimelineEntryContentMarshaller) {

  def apply(entry: TimelineEntry): urt.TimelineEntry =
    urt.TimelineEntry(
      entryId = entry.entryIdentifier,
      sortIndetwitter = entry.sortIndetwitter.getOrElse(throw new TimelineEntryMissingSortIndetwitterEtwitterception),
      content = timelineEntryContentMarshaller(entry),
      etwitterpiryTime = entry.etwitterpirationTimeInMillis
    )
}

class TimelineEntryMissingSortIndetwitterEtwitterception
    etwittertends UnsupportedOperationEtwitterception("Timeline entry missing sort indetwitter")
