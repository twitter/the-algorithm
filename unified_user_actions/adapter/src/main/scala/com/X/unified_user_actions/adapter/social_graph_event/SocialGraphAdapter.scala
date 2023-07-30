package com.X.unified_user_actions.adapter.social_graph_event

import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.kafka.serde.UnKeyed
import com.X.socialgraph.thriftscala.Action._
import com.X.socialgraph.thriftscala.WriteEvent
import com.X.socialgraph.thriftscala.{Action => SocialGraphAction}
import com.X.unified_user_actions.adapter.AbstractAdapter
import com.X.unified_user_actions.adapter.social_graph_event.SocialGraphEngagement._
import com.X.unified_user_actions.thriftscala.UnifiedUserAction

class SocialGraphAdapter extends AbstractAdapter[WriteEvent, UnKeyed, UnifiedUserAction] {

  import SocialGraphAdapter._

  override def adaptOneToKeyedMany(
    input: WriteEvent,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[(UnKeyed, UnifiedUserAction)] =
    adaptEvent(input).map { e => (UnKeyed, e) }
}

object SocialGraphAdapter {

  def adaptEvent(writeEvent: WriteEvent): Seq[UnifiedUserAction] =
    Option(writeEvent).flatMap { e =>
      socialGraphWriteEventTypeToUuaEngagementType.get(e.action)
    } match {
      case Some(uuaAction) => uuaAction.toUnifiedUserAction(writeEvent, uuaAction)
      case None => Nil
    }

  private val socialGraphWriteEventTypeToUuaEngagementType: Map[
    SocialGraphAction,
    BaseSocialGraphWriteEvent[_]
  ] =
    Map[SocialGraphAction, BaseSocialGraphWriteEvent[_]](
      Follow -> ProfileFollow,
      Unfollow -> ProfileUnfollow,
      Block -> ProfileBlock,
      Unblock -> ProfileUnblock,
      Mute -> ProfileMute,
      Unmute -> ProfileUnmute,
      ReportAsSpam -> ProfileReportAsSpam,
      ReportAsAbuse -> ProfileReportAsAbuse
    )
}
