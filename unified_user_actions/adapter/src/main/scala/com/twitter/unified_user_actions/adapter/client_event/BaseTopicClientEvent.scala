package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Top c nfo

abstract class BaseTop cCl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {
  overr de def  s emTypeVal d( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeF lterPred cates. s emTypeTop c( emTypeOpt)

  overr de def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] =
    for (act onTop c d <- Cl entEventCommonUt ls.getTop c d(
        ce em = ce em,
        ceNa spaceOpt = logEvent.eventNa space))
      y eld  em.Top c nfo(Top c nfo(act onTop c d = act onTop c d))
}
