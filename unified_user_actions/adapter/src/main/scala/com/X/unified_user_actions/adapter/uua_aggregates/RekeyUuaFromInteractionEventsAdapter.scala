package com.X.unified_user_actions.adapter.uua_aggregates

import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.iesource.thriftscala.ClientEventContext
import com.X.iesource.thriftscala.EngagingContext
import com.X.unified_user_actions.adapter.AbstractAdapter
import com.X.iesource.thriftscala.InteractionType
import com.X.iesource.thriftscala.InteractionEvent
import com.X.unified_user_actions.adapter.common.AdapterUtils
import com.X.unified_user_actions.thriftscala.ActionType
import com.X.unified_user_actions.thriftscala.EventMetadata
import com.X.unified_user_actions.thriftscala.KeyedUuaTweet
import com.X.unified_user_actions.thriftscala.SourceLineage
import com.X.unified_user_actions.thriftscala.UserIdentifier

/**
 * This is to read directly from InteractionEvents
 */
class RekeyUuaFromInteractionEventsAdapter
    extends AbstractAdapter[InteractionEvent, Long, KeyedUuaTweet] {

  import RekeyUuaFromInteractionEventsAdapter._
  override def adaptOneToKeyedMany(
    input: InteractionEvent,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[(Long, KeyedUuaTweet)] =
    adaptEvent(input, statsReceiver).map { e => (e.tweetId, e) }
}

object RekeyUuaFromInteractionEventsAdapter {

  def adaptEvent(
    e: InteractionEvent,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[KeyedUuaTweet] =
    Option(e).flatMap { e =>
      e.interactionType.flatMap {
        case InteractionType.TweetRenderImpression if !isDetailImpression(e.engagingContext) =>
          getRekeyedUUA(
            input = e,
            actionType = ActionType.ClientTweetRenderImpression,
            sourceLineage = SourceLineage.ClientEvents,
            statsReceiver = statsReceiver)
        case _ => None
      }
    }.toSeq

  def getRekeyedUUA(
    input: InteractionEvent,
    actionType: ActionType,
    sourceLineage: SourceLineage,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Option[KeyedUuaTweet] =
    input.engagingUserId match {
      // please see https://docs.google.com/document/d/1-fy2S-8-YMRQgEN0Sco0OLTmeOIUdqgiZ5G1KwTHt2g/edit#
      // in order to withstand of potential attacks, we filter out the logged-out users.
      // Checking user id is 0 is the reverse engineering of
      // https://sourcegraph.X.biz/git.X.biz/source/-/blob/iesource/thrift/src/main/thrift/com/X/iesource/interaction_event.thrift?L220
      // https://sourcegraph.X.biz/git.X.biz/source/-/blob/iesource/common/src/main/scala/com/X/iesource/common/converters/client/LogEventConverter.scala?L198
      case 0L =>
        statsReceiver.counter("loggedOutEvents").incr()
        None
      case _ =>
        Some(
          KeyedUuaTweet(
            tweetId = input.targetId,
            actionType = actionType,
            userIdentifier = UserIdentifier(userId = Some(input.engagingUserId)),
            eventMetadata = EventMetadata(
              sourceTimestampMs = input.triggeredTimestampMillis.getOrElse(input.timestampMillis),
              receivedTimestampMs = AdapterUtils.currentTimestampMs,
              sourceLineage = sourceLineage
            )
          ))
    }

  def isDetailImpression(engagingContext: EngagingContext): Boolean =
    engagingContext match {
      case EngagingContext.ClientEventContext(
            ClientEventContext(_, _, _, _, _, _, _, Some(isDetailsImpression), _)
          ) if isDetailsImpression =>
        true
      case _ => false
    }
}
