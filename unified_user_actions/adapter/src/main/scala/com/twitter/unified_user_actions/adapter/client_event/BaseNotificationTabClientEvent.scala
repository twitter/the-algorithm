package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

abstract class BaseNot f cat onTabCl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {

  //  emType  s `None` for Not f cat on Tab events
  overr de def  s emTypeVal d( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeF lterPred cates. gnore emType( emTypeOpt)

  overr de def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] = for {
    not f cat onTabDeta ls <- ce em.not f cat onTabDeta ls
    cl entEvent tadata <- not f cat onTabDeta ls.cl entEvent tadata
    not f cat on d <- Not f cat onCl entEventUt ls.getNot f cat on dForNot f cat onTab(ce em)
  } y eld {
    cl entEvent tadata.t et ds match {
      //  f `t et ds` conta n more than one T et  d, create `Mult T etNot f cat on`
      case So (t et ds)  f t et ds.s ze > 1 =>
         em.Not f cat on nfo(
          Not f cat on nfo(
            act onNot f cat on d = not f cat on d,
            content = Not f cat onContent.Mult T etNot f cat on(
              Mult T etNot f cat on(t et ds = t et ds))
          ))
      //  f `t et ds` conta n exactly one T et  d, create `T etNot f cat on`
      case So (t et ds)  f t et ds.s ze == 1 =>
         em.Not f cat on nfo(
          Not f cat on nfo(
            act onNot f cat on d = not f cat on d,
            content =
              Not f cat onContent.T etNot f cat on(T etNot f cat on(t et d = t et ds. ad))))
      //  f `t et ds` are m ss ng, create `UnknownNot f cat on`
      case _ =>
         em.Not f cat on nfo(
          Not f cat on nfo(
            act onNot f cat on d = not f cat on d,
            content = Not f cat onContent.UnknownNot f cat on(UnknownNot f cat on())
          ))
    }
  }
}
