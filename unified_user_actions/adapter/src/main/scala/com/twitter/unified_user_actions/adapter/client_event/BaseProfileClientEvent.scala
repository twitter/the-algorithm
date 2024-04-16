package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.Cl entEventCommonUt ls.getProf le dFromUser em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Prof le nfo

abstract class BaseProf leCl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {
  overr de def  s emTypeVal d( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeF lterPred cates. s emTypeProf le( emTypeOpt)

  overr de def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] =
    getProf le dFromUser em(ce em).map {  d =>
       em.Prof le nfo(
        Prof le nfo(act onProf le d =  d)
      )
    }
}
