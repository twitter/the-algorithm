package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}
import com.twitter.timelines.model.TweetId

object PriorSeenEntries {
  def fromThrift(entries: thrift.PriorSeenEntries): PriorSeenEntries = {
    PriorSeenEntries(seenEntries = entries.seenEntries)
  }
}

case class PriorSeenEntries(seenEntries: Seq[TweetId]) {

  throwIfInvalid()

  def toThrift: thrift.PriorSeenEntries = {
    thrift.PriorSeenEntries(seenEntries = seenEntries)
  }

  def throwIfInvalid(): Unit = {
    // No validation performed.
  }
}
