package com.tw ter.un f ed_user_act ons.adapter.favor e_arch val_events

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.t  l neserv ce.fanout.thr ftscala.Favor eArch valEvent
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

class Favor eArch valEventsAdapter
    extends AbstractAdapter[Favor eArch valEvent, UnKeyed, Un f edUserAct on] {

   mport Favor eArch valEventsAdapter._
  overr de def adaptOneToKeyedMany(
     nput: Favor eArch valEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent( nput).map { e => (UnKeyed, e) }
}

object Favor eArch valEventsAdapter {

  def adaptEvent(e: Favor eArch valEvent): Seq[Un f edUserAct on] =
    Opt on(e).map { e =>
      Un f edUserAct on(
        user dent f er = User dent f er(user d = So (e.favor er d)),
         em = get em(e),
        act onType =
           f (e. sArch v ngAct on.getOrElse(true)) Act onType.ServerT etArch veFavor e
          else Act onType.ServerT etUnarch veFavor e,
        event tadata = getEvent tadata(e)
      )
    }.toSeq

  def get em(e: Favor eArch valEvent):  em =
     em.T et nfo(
      T et nfo(
        // Please note that  re   always use T et d (not s ceT et d)!!!
        act onT et d = e.t et d,
        act onT etAuthor nfo = So (Author nfo(author d = e.t etUser d)),
        ret etedT et d = e.s ceT et d
      )
    )

  def getEvent tadata(e: Favor eArch valEvent): Event tadata =
    Event tadata(
      s ceT  stampMs = e.t  stampMs,
      rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
      s ceL neage = S ceL neage.ServerFavor eArch valEvents,
    )
}
