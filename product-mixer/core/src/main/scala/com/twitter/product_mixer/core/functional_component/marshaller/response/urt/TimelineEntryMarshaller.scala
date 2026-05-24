package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineEntryMarshaller @Inject() (
  timelineEntryContentMarshaller: TimelineEntryContentMarshaller) {

  def apply(entry: TimelineEntry): urt.TimelineEntry =
    urt.TimelineEntry(
      entryId = entry.entryIdentifier,
      sortIndex = entry.sortIndex.getOrElse(throw new TimelineEntryMissingSortIndexException),
      content = timelineEntryContentMarshaller(entry),
      expiryTime = entry.expirationTimeInMillis
    )
}

class TimelineEntryMissingSortIndexException
    extends UnsupportedOperationException("Timeline entry missing sort index")
