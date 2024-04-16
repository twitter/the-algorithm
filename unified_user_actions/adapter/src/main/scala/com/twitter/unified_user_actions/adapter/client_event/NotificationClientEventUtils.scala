package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}

object Not f cat onCl entEventUt ls {

  // Not f cat on  d for not f cat on  n t  Not f cat on Tab
  def getNot f cat on dForNot f cat onTab(
    ce em: LogEvent em
  ): Opt on[Str ng] = {
    for {
      not f cat onTabDeta ls <- ce em.not f cat onTabDeta ls
      cl entEvent taData <- not f cat onTabDeta ls.cl entEvent tadata
      not f cat on d <- cl entEvent taData.upstream d
    } y eld {
      not f cat on d
    }
  }

  // Not f cat on  d for Push Not f cat on
  def getNot f cat on dForPushNot f cat on(logEvent: LogEvent): Opt on[Str ng] = for {
    pushNot f cat onDeta ls <- logEvent.not f cat onDeta ls
    not f cat on d <- pushNot f cat onDeta ls. mpress on d
  } y eld not f cat on d
}
