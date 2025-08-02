package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}
import com.twitter.timelineservice.model.TimelineId

object ReverseChronTimelineQuery {
  def fromTimelineQuery(query: TimelineQuery): ReverseChronTimelineQuery = {
    query match {
      case q: ReverseChronTimelineQuery => q
      case _ => throw new IllegalArgumentEtwitterception(s"Unsupported query type: $query")
    }
  }
}

case class ReverseChronTimelineQuery(
  override val id: TimelineId,
  override val matwitterCount: Option[Int] = None,
  override val range: Option[TimelineRange] = None,
  override val options: Option[ReverseChronTimelineQueryOptions] = None)
    etwittertends TimelineQuery(thrift.TimelineQueryType.ReverseChron, id, matwitterCount, range, options) {

  throwIfInvalid()
}
