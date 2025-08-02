package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}
import com.twitter.timelines.model.UserId
import com.twitter.timelineservice.model.TimelineId

object TimelineQuery {
  def fromThrift(query: thrift.TimelineQuery): TimelineQuery = {
    val queryType = query.queryType
    val id = TimelineId.fromThrift(query.timelineId)
    val matwitterCount = query.matwitterCount
    val range = query.range.map(TimelineRange.fromThrift)
    val options = query.options.map(TimelineQueryOptions.fromThrift)

    queryType match {
      case thrift.TimelineQueryType.Ranked =>
        val rankedOptions = getRankedOptions(options)
        RankedTimelineQuery(id, matwitterCount, range, rankedOptions)

      case thrift.TimelineQueryType.ReverseChron =>
        val reverseChronOptions = getReverseChronOptions(options)
        ReverseChronTimelineQuery(id, matwitterCount, range, reverseChronOptions)

      case _ =>
        throw new IllegalArgumentEtwitterception(s"Unsupported query type: $queryType")
    }
  }

  def getRankedOptions(
    options: Option[TimelineQueryOptions]
  ): Option[RankedTimelineQueryOptions] = {
    options.map {
      case o: RankedTimelineQueryOptions => o
      case _ =>
        throw new IllegalArgumentEtwitterception(
          "Only RankedTimelineQueryOptions are supported when queryType is TimelineQueryType.Ranked"
        )
    }
  }

  def getReverseChronOptions(
    options: Option[TimelineQueryOptions]
  ): Option[ReverseChronTimelineQueryOptions] = {
    options.map {
      case o: ReverseChronTimelineQueryOptions => o
      case _ =>
        throw new IllegalArgumentEtwitterception(
          "Only ReverseChronTimelineQueryOptions are supported when queryType is TimelineQueryType.ReverseChron"
        )
    }
  }
}

abstract class TimelineQuery(
  private val queryType: thrift.TimelineQueryType,
  val id: TimelineId,
  val matwitterCount: Option[Int],
  val range: Option[TimelineRange],
  val options: Option[TimelineQueryOptions]) {

  throwIfInvalid()

  def userId: UserId = {
    id.id
  }

  def throwIfInvalid(): Unit = {
    Timeline.throwIfIdInvalid(id)
    range.foreach(_.throwIfInvalid())
    options.foreach(_.throwIfInvalid())
  }

  def toThrift: thrift.TimelineQuery = {
    thrift.TimelineQuery(
      queryType = queryType,
      timelineId = id.toThrift,
      matwitterCount = matwitterCount,
      range = range.map(_.toTimelineRangeThrift),
      options = options.map(_.toTimelineQueryOptionsThrift)
    )
  }
}
