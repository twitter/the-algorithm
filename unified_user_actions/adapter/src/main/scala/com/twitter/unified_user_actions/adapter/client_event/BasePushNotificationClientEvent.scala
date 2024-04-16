package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

abstract class BasePushNot f cat onCl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {

  overr de def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] = for {
     em d <- ce em. d
    not f cat on d <- Not f cat onCl entEventUt ls.getNot f cat on dForPushNot f cat on(logEvent)
  } y eld {
     em.Not f cat on nfo(
      Not f cat on nfo(
        act onNot f cat on d = not f cat on d,
        content = Not f cat onContent.T etNot f cat on(T etNot f cat on(t et d =  em d))))
  }
}
