package com.X.timelineranker.model

import com.X.timelineranker.{thriftscala => thrift}
import com.X.timelineservice.model.TimelineId

case class RankedTimelineQuery(
  override val id: TimelineId,
  override val maxCount: Option[Int] = None,
  override val range: Option[TimelineRange] = None,
  override val options: Option[RankedTimelineQueryOptions] = None)
    extends TimelineQuery(thrift.TimelineQueryType.Ranked, id, maxCount, range, options) {

  throwIfInvalid()
}
