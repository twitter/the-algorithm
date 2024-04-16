package com.tw ter.un f ed_user_act ons.adapter.soc al_graph_event

 mport com.tw ter.soc algraph.thr ftscala.Act on
 mport com.tw ter.soc algraph.thr ftscala.BlockGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.FollowGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.MuteGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.ReportAsAbuseGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.ReportAsSpamGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.Wr eEvent
 mport com.tw ter.soc algraph.thr ftscala.Wr eRequestResult
 mport com.tw ter.un f ed_user_act ons.thr ftscala.{Act onType => UuaAct onType}

object Soc alGraphEngage nt {

  /**
   * T   s "Follow" event to  nd cate user1 follows user2 captured  n ServerProf leFollow
   */
  object Prof leFollow extends BaseSoc alGraphWr eEvent[FollowGraphEvent] {
    overr de def uuaAct onType: UuaAct onType = UuaAct onType.ServerProf leFollow

    overr de def getSubType(
      e: Wr eEvent
    ): Opt on[Seq[FollowGraphEvent]] =
      e.follow

    overr de def getWr eRequestResultFromSubType(
      e: Seq[FollowGraphEvent]
    ): Seq[Wr eRequestResult] = {
      // Remove all redundant operat ons (FollowGraphEvent.redundantOperat on == So (true))
      e.collect {
        case fe  f !fe.redundantOperat on.getOrElse(false) => fe.result
      }
    }
  }

  /**
   * T   s "Unfollow" event to  nd cate user1 unfollows user2 captured  n ServerProf leUnfollow
   *
   * Both Unfollow and Follow use t  struct FollowGraphEvent, but are treated  n  s  nd v dual case
   * class.
   */
  object Prof leUnfollow extends BaseSoc alGraphWr eEvent[FollowGraphEvent] {
    overr de def uuaAct onType: UuaAct onType = UuaAct onType.ServerProf leUnfollow

    overr de def getSubType(
      e: Wr eEvent
    ): Opt on[Seq[FollowGraphEvent]] =
      e.follow

    overr de def getWr eRequestResultFromSubType(
      e: Seq[FollowGraphEvent]
    ): Seq[Wr eRequestResult] =
      e.collect {
        case fe  f !fe.redundantOperat on.getOrElse(false) => fe.result
      }
  }

  /**
   * T   s "Block" event to  nd cate user1 blocks user2 captured  n ServerProf leBlock
   */
  object Prof leBlock extends BaseSoc alGraphWr eEvent[BlockGraphEvent] {
    overr de def uuaAct onType: UuaAct onType = UuaAct onType.ServerProf leBlock

    overr de def getSubType(
      e: Wr eEvent
    ): Opt on[Seq[BlockGraphEvent]] =
      e.block

    overr de def getWr eRequestResultFromSubType(
      e: Seq[BlockGraphEvent]
    ): Seq[Wr eRequestResult] =
      e.map(_.result)
  }

  /**
   * T   s "Unblock" event to  nd cate user1 unblocks user2 captured  n ServerProf leUnblock
   *
   * Both Unblock and Block use struct BlockGraphEvent, but are treated  n  s  nd v dual case
   * class.
   */
  object Prof leUnblock extends BaseSoc alGraphWr eEvent[BlockGraphEvent] {
    overr de def uuaAct onType: UuaAct onType = UuaAct onType.ServerProf leUnblock

    overr de def getSubType(
      e: Wr eEvent
    ): Opt on[Seq[BlockGraphEvent]] =
      e.block

    overr de def getWr eRequestResultFromSubType(
      e: Seq[BlockGraphEvent]
    ): Seq[Wr eRequestResult] =
      e.map(_.result)
  }

  /**
   * T   s "Mute" event to  nd cate user1 mutes user2 captured  n ServerProf leMute
   */
  object Prof leMute extends BaseSoc alGraphWr eEvent[MuteGraphEvent] {
    overr de def uuaAct onType: UuaAct onType = UuaAct onType.ServerProf leMute

    overr de def getSubType(
      e: Wr eEvent
    ): Opt on[Seq[MuteGraphEvent]] =
      e.mute

    overr de def getWr eRequestResultFromSubType(e: Seq[MuteGraphEvent]): Seq[Wr eRequestResult] =
      e.map(_.result)
  }

  /**
   * T   s "Unmute" event to  nd cate user1 unmutes user2 captured  n ServerProf leUnmute
   *
   * Both Unmute and Mute use t  struct MuteGraphEvent, but are treated  n  s  nd v dual case
   * class.
   */
  object Prof leUnmute extends BaseSoc alGraphWr eEvent[MuteGraphEvent] {
    overr de def uuaAct onType: UuaAct onType = UuaAct onType.ServerProf leUnmute

    overr de def getSubType(
      e: Wr eEvent
    ): Opt on[Seq[MuteGraphEvent]] =
      e.mute

    overr de def getWr eRequestResultFromSubType(e: Seq[MuteGraphEvent]): Seq[Wr eRequestResult] =
      e.map(_.result)
  }

  object Prof leReportAsSpam extends BaseReportSoc alGraphWr eEvent[ReportAsSpamGraphEvent] {
    overr de def uuaAct onType: UuaAct onType = UuaAct onType.ServerProf leReport
    overr de def soc alGraphAct on: Act on = Act on.ReportAsSpam

    overr de def getSubType(
      e: Wr eEvent
    ): Opt on[Seq[ReportAsSpamGraphEvent]] =
      e.reportAsSpam

    overr de def getWr eRequestResultFromSubType(
      e: Seq[ReportAsSpamGraphEvent]
    ): Seq[Wr eRequestResult] =
      e.map(_.result)
  }

  object Prof leReportAsAbuse extends BaseReportSoc alGraphWr eEvent[ReportAsAbuseGraphEvent] {
    overr de def uuaAct onType: UuaAct onType = UuaAct onType.ServerProf leReport
    overr de def soc alGraphAct on: Act on = Act on.ReportAsAbuse

    overr de def getSubType(
      e: Wr eEvent
    ): Opt on[Seq[ReportAsAbuseGraphEvent]] =
      e.reportAsAbuse

    overr de def getWr eRequestResultFromSubType(
      e: Seq[ReportAsAbuseGraphEvent]
    ): Seq[Wr eRequestResult] =
      e.map(_.result)
  }
}
