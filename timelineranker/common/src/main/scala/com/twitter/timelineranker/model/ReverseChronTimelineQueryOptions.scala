package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}

object ReverseChronTimelineQueryOptions {
  val Default: ReverseChronTimelineQueryOptions = ReverseChronTimelineQueryOptions()

  def fromThrift(
    options: thrift.ReverseChronTimelineQueryOptions
  ): ReverseChronTimelineQueryOptions = {
    ReverseChronTimelineQueryOptions(
      getTweetsFromArchiveIndetwitter = options.getTweetsFromArchiveIndetwitter
    )
  }
}

case class ReverseChronTimelineQueryOptions(getTweetsFromArchiveIndetwitter: Boolean = true)
    etwittertends TimelineQueryOptions {

  throwIfInvalid()

  def toThrift: thrift.ReverseChronTimelineQueryOptions = {
    thrift.ReverseChronTimelineQueryOptions(getTweetsFromArchiveIndetwitter = getTweetsFromArchiveIndetwitter)
  }

  def toTimelineQueryOptionsThrift: thrift.TimelineQueryOptions = {
    thrift.TimelineQueryOptions.ReverseChronTimelineQueryOptions(toThrift)
  }

  def throwIfInvalid(): Unit = {}
}
