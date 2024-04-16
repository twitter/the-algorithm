package com.tw ter.un f ed_user_act ons.adapter.ret et_arch val_events

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.t etyp e.thr ftscala.Ret etArch valEvent
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

class Ret etArch valEventsAdapter
    extends AbstractAdapter[Ret etArch valEvent, UnKeyed, Un f edUserAct on] {

   mport Ret etArch valEventsAdapter._
  overr de def adaptOneToKeyedMany(
     nput: Ret etArch valEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent( nput).map { e => (UnKeyed, e) }
}

object Ret etArch valEventsAdapter {

  def adaptEvent(e: Ret etArch valEvent): Seq[Un f edUserAct on] =
    Opt on(e).map { e =>
      Un f edUserAct on(
        user dent f er = User dent f er(user d = So (e.ret etUser d)),
         em = get em(e),
        act onType =
           f (e. sArch v ngAct on.getOrElse(true)) Act onType.ServerT etArch veRet et
          else Act onType.ServerT etUnarch veRet et,
        event tadata = getEvent tadata(e)
      )
    }.toSeq

  def get em(e: Ret etArch valEvent):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = e.srcT et d,
        act onT etAuthor nfo = So (Author nfo(author d = So (e.srcT etUser d))),
        ret et ngT et d = So (e.ret et d)
      )
    )

  def getEvent tadata(e: Ret etArch valEvent): Event tadata =
    Event tadata(
      s ceT  stampMs = e.t  stampMs,
      rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
      s ceL neage = S ceL neage.ServerRet etArch valEvents,
    )
}
