package com.tw ter.un f ed_user_act ons.adapter.soc al_graph_event

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.soc algraph.thr ftscala.Act on._
 mport com.tw ter.soc algraph.thr ftscala.Wr eEvent
 mport com.tw ter.soc algraph.thr ftscala.{Act on => Soc alGraphAct on}
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.adapter.soc al_graph_event.Soc alGraphEngage nt._
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on

class Soc alGraphAdapter extends AbstractAdapter[Wr eEvent, UnKeyed, Un f edUserAct on] {

   mport Soc alGraphAdapter._

  overr de def adaptOneToKeyedMany(
     nput: Wr eEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent( nput).map { e => (UnKeyed, e) }
}

object Soc alGraphAdapter {

  def adaptEvent(wr eEvent: Wr eEvent): Seq[Un f edUserAct on] =
    Opt on(wr eEvent).flatMap { e =>
      soc alGraphWr eEventTypeToUuaEngage ntType.get(e.act on)
    } match {
      case So (uuaAct on) => uuaAct on.toUn f edUserAct on(wr eEvent, uuaAct on)
      case None => N l
    }

  pr vate val soc alGraphWr eEventTypeToUuaEngage ntType: Map[
    Soc alGraphAct on,
    BaseSoc alGraphWr eEvent[_]
  ] =
    Map[Soc alGraphAct on, BaseSoc alGraphWr eEvent[_]](
      Follow -> Prof leFollow,
      Unfollow -> Prof leUnfollow,
      Block -> Prof leBlock,
      Unblock -> Prof leUnblock,
      Mute -> Prof leMute,
      Unmute -> Prof leUnmute,
      ReportAsSpam -> Prof leReportAsSpam,
      ReportAsAbuse -> Prof leReportAsAbuse
    )
}
