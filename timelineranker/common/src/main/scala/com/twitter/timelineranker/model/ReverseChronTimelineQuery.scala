package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}
import com.twitter.timelineservice.model.TimelineId

object ReverseChronTimelineQuery {
  def fromTimelineQuery(query: TimelineQuery): ReverseChronTimelineQuery = {
    query match {
      case q: ReverseChronTimelineQuery => q
      case _ => throw new IllegalArgumentException(s"Unsupported query type: $query")
    }
  }
}

case class ReverseChronTimelineQuery(
  override val id: TimelineId,
  override val maxCount: Option[Int] = None,
  override val range: Option[TimelineRange] = None,
  override val options: Option[ReverseChronTimelineQueryOptions] = None)
    extends TimelineQuery(thrift.TimelineQueryType.ReverseChron, id, maxCount, range, options) {

  throwIfInvalid()
}
