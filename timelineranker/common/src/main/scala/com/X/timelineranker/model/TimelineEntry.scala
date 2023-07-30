package com.X.timelineranker.model

import com.X.timelineranker.{thriftscala => thrift}

object TimelineEntry {
  def fromThrift(entry: thrift.TimelineEntry): TimelineEntry = {
    entry match {
      case thrift.TimelineEntry.Tweet(e) => Tweet.fromThrift(e)
      case thrift.TimelineEntry.TweetypieTweet(e) => new HydratedTweetEntry(e)
      case _ => throw new IllegalArgumentException(s"Unsupported type: $entry")
    }
  }
}

trait TimelineEntry {
  def toTimelineEntryThrift: thrift.TimelineEntry
  def throwIfInvalid(): Unit
}
