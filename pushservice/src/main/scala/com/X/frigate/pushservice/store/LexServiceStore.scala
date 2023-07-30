package com.X.frigate.pushservice.store

import com.X.livevideo.common.ids.EventId
import com.X.livevideo.timeline.client.v2.LiveVideoTimelineClient
import com.X.livevideo.timeline.domain.v2.Event
import com.X.livevideo.timeline.domain.v2.LookupContext
import com.X.stitch.storehaus.ReadableStoreOfStitch
import com.X.stitch.NotFound
import com.X.stitch.Stitch
import com.X.storehaus.ReadableStore

case class EventRequest(eventId: Long, lookupContext: LookupContext = LookupContext.default)

object LexServiceStore {
  def apply(
    liveVideoTimelineClient: LiveVideoTimelineClient
  ): ReadableStore[EventRequest, Event] = {
    ReadableStoreOfStitch { eventRequest =>
      liveVideoTimelineClient.getEvent(
        EventId(eventRequest.eventId),
        eventRequest.lookupContext) rescue {
        case NotFound => Stitch.NotFound
      }
    }
  }
}
