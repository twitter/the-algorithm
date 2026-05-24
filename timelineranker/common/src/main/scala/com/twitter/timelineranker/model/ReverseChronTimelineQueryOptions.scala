package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}

object ReverseChronTimelineQueryOptions {
  val Default: ReverseChronTimelineQueryOptions = ReverseChronTimelineQueryOptions()

  def fromThrift(
    options: thrift.ReverseChronTimelineQueryOptions
  ): ReverseChronTimelineQueryOptions = {
    ReverseChronTimelineQueryOptions(
      getTweetsFromArchiveIndex = options.getTweetsFromArchiveIndex
    )
  }
}

case class ReverseChronTimelineQueryOptions(getTweetsFromArchiveIndex: Boolean = true)
    extends TimelineQueryOptions {

  throwIfInvalid()

  def toThrift: thrift.ReverseChronTimelineQueryOptions = {
    thrift.ReverseChronTimelineQueryOptions(getTweetsFromArchiveIndex = getTweetsFromArchiveIndex)
  }

  def toTimelineQueryOptionsThrift: thrift.TimelineQueryOptions = {
    thrift.TimelineQueryOptions.ReverseChronTimelineQueryOptions(toThrift)
  }

  def throwIfInvalid(): Unit = {}
}
