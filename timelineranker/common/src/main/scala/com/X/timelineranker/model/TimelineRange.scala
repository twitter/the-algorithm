package com.X.timelineranker.model

import com.X.timelineranker.{thriftscala => thrift}

object TimelineRange {
  def fromThrift(range: thrift.TimelineRange): TimelineRange = {
    range match {
      case thrift.TimelineRange.TimeRange(r) => TimeRange.fromThrift(r)
      case thrift.TimelineRange.TweetIdRange(r) => TweetIdRange.fromThrift(r)
      case _ => throw new IllegalArgumentException(s"Unsupported type: $range")
    }
  }
}

trait TimelineRange {
  def toTimelineRangeThrift: thrift.TimelineRange
  def throwIfInvalid(): Unit
}
