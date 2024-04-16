package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

abstract class BaseV deoCl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {

  overr de def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] = for {
    act onT et d <- ce em. d
    cl ent d aEvent <- ce em.cl ent d aEvent
    sess onState <- cl ent d aEvent.sess onState
     d a dent f er <- sess onState.contentV deo dent f er
     d a d <- V deoCl entEventUt ls.v deo dFrom d a dent f er( d a dent f er)
     d aDeta ls <- ce em. d aDeta lsV2
     d a ems <-  d aDeta ls. d a ems
    v deo tadata <- V deoCl entEventUt ls.getV deo tadata(
       d a d,
       d a ems,
      ce em.cardDeta ls.flatMap(_.ampl fyDeta ls))
  } y eld {
     em.T et nfo(
      Cl entEventCommonUt ls
        .getBas cT et nfo(
          act onT et d = act onT et d,
          ce em = ce em,
          ceNa spaceOpt = logEvent.eventNa space)
        .copy(t etAct on nfo = So (v deo tadata)))
  }
}
