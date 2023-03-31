package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}

object TimelineQueryOptions {
  def fromThrift(options: thrift.TimelineQueryOptions): TimelineQueryOptions = {
    options match {
      case thrift.TimelineQueryOptions.RankedTimelineQueryOptions(r) =>
        RankedTimelineQueryOptions.fromThrift(r)
      case thrift.TimelineQueryOptions.ReverseChronTimelineQueryOptions(r) =>
        ReverseChronTimelineQueryOptions.fromThrift(r)
      case _ => throw new IllegalArgumentException(s"Unsupported type: $options")
    }
  }
}

trait TimelineQueryOptions {
  def toTimelineQueryOptionsThrift: thrift.TimelineQueryOptions
  def throwIfInvalid(): Unit
}
