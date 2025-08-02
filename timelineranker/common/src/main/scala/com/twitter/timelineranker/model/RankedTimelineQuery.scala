package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}
import com.twitter.timelineservice.model.TimelineId

case class RankedTimelineQuery(
  override val id: TimelineId,
  override val matwitterCount: Option[Int] = None,
  override val range: Option[TimelineRange] = None,
  override val options: Option[RankedTimelineQueryOptions] = None)
    etwittertends TimelineQuery(thrift.TimelineQueryType.Ranked, id, matwitterCount, range, options) {

  throwIfInvalid()
}
