package com.X.timelineranker.model

import com.X.timelineranker.{thriftscala => thrift}

object TimelineEntryEnvelope {
  def fromThrift(entryEnvelope: thrift.TimelineEntryEnvelope): TimelineEntryEnvelope = {
    TimelineEntryEnvelope(
      entry = TimelineEntry.fromThrift(entryEnvelope.entry)
    )
  }
}

case class TimelineEntryEnvelope(entry: TimelineEntry) {

  throwIfInvalid()

  def toThrift: thrift.TimelineEntryEnvelope = {
    thrift.TimelineEntryEnvelope(entry.toTimelineEntryThrift)
  }

  def throwIfInvalid(): Unit = {
    entry.throwIfInvalid()
  }
}
