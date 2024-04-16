package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Card nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em

abstract class BaseCardCl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {

  overr de def  s emTypeVal d( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeF lterPred cates. gnore emType( emTypeOpt)
  overr de def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] = So (
     em.Card nfo(
      Card nfo(
         d = ce em. d,
         emType = ce em. emType,
        act onT etAuthor nfo = Cl entEventCommonUt ls.getAuthor nfo(ce em),
      ))
  )
}
