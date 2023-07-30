package com.X.unified_user_actions.adapter.retweet_archival_events

import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.kafka.serde.UnKeyed
import com.X.tweetypie.thriftscala.RetweetArchivalEvent
import com.X.unified_user_actions.adapter.AbstractAdapter
import com.X.unified_user_actions.adapter.common.AdapterUtils
import com.X.unified_user_actions.thriftscala._

class RetweetArchivalEventsAdapter
    extends AbstractAdapter[RetweetArchivalEvent, UnKeyed, UnifiedUserAction] {

  import RetweetArchivalEventsAdapter._
  override def adaptOneToKeyedMany(
    input: RetweetArchivalEvent,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[(UnKeyed, UnifiedUserAction)] =
    adaptEvent(input).map { e => (UnKeyed, e) }
}

object RetweetArchivalEventsAdapter {

  def adaptEvent(e: RetweetArchivalEvent): Seq[UnifiedUserAction] =
    Option(e).map { e =>
      UnifiedUserAction(
        userIdentifier = UserIdentifier(userId = Some(e.retweetUserId)),
        item = getItem(e),
        actionType =
          if (e.isArchivingAction.getOrElse(true)) ActionType.ServerTweetArchiveRetweet
          else ActionType.ServerTweetUnarchiveRetweet,
        eventMetadata = getEventMetadata(e)
      )
    }.toSeq

  def getItem(e: RetweetArchivalEvent): Item =
    Item.TweetInfo(
      TweetInfo(
        actionTweetId = e.srcTweetId,
        actionTweetAuthorInfo = Some(AuthorInfo(authorId = Some(e.srcTweetUserId))),
        retweetingTweetId = Some(e.retweetId)
      )
    )

  def getEventMetadata(e: RetweetArchivalEvent): EventMetadata =
    EventMetadata(
      sourceTimestampMs = e.timestampMs,
      receivedTimestampMs = AdapterUtils.currentTimestampMs,
      sourceLineage = SourceLineage.ServerRetweetArchivalEvents,
    )
}
