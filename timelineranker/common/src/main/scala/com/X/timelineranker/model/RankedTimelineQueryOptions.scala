package com.X.timelineranker.model

import com.X.timelineranker.{thriftscala => thrift}

object RankedTimelineQueryOptions {
  def fromThrift(options: thrift.RankedTimelineQueryOptions): RankedTimelineQueryOptions = {
    RankedTimelineQueryOptions(
      seenEntries = options.seenEntries.map(PriorSeenEntries.fromThrift)
    )
  }
}

case class RankedTimelineQueryOptions(seenEntries: Option[PriorSeenEntries])
    extends TimelineQueryOptions {

  throwIfInvalid()

  def toThrift: thrift.RankedTimelineQueryOptions = {
    thrift.RankedTimelineQueryOptions(seenEntries = seenEntries.map(_.toThrift))
  }

  def toTimelineQueryOptionsThrift: thrift.TimelineQueryOptions = {
    thrift.TimelineQueryOptions.RankedTimelineQueryOptions(toThrift)
  }

  def throwIfInvalid(): Unit = {
    seenEntries.foreach(_.throwIfInvalid)
  }
}
