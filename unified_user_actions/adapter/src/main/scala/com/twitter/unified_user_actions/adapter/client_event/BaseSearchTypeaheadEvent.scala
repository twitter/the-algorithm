package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.logbase.thr ftscala.LogBase
 mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.Cl entEventCommonUt ls.getProf le dFromUser em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.ProductSurface
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Top cQueryResult
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Typea adAct on nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Typea ad nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.User dent f er
 mport com.tw ter.un f ed_user_act ons.thr ftscala.UserResult

abstract class BaseSearchTypea adEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {

  overr de def toUn f edUserAct on(logEvent: LogEvent): Seq[Un f edUserAct on] = {
    val logBase: Opt on[LogBase] = logEvent.logBase

    for {
      ed <- logEvent.eventDeta ls.toSeq
      targets <- ed.targets.toSeq
      ceTarget <- targets
      eventT  stamp <- logBase.flatMap(getS ceT  stamp)
      uua em <- getUua em(ceTarget, logEvent)
       f  s emTypeVal d(ceTarget. emType)
    } y eld {
      val user dent f er: User dent f er = User dent f er(
        user d = logBase.flatMap(_.user d),
        guest dMarket ng = logBase.flatMap(_.guest dMarket ng))

      val productSurface: Opt on[ProductSurface] = ProductSurfaceUt ls
        .getProductSurface(logEvent.eventNa space)

      val event taData: Event tadata = Cl entEventCommonUt ls
        .getEvent tadata(
          eventT  stamp = eventT  stamp,
          logEvent = logEvent,
          ce em = ceTarget,
          productSurface = productSurface
        )

      Un f edUserAct on(
        user dent f er = user dent f er,
         em = uua em,
        act onType = act onType,
        event tadata = event taData,
        productSurface = productSurface,
        productSurface nfo =
          ProductSurfaceUt ls.getProductSurface nfo(productSurface, ceTarget, logEvent)
      )
    }
  }
  overr de def  s emTypeVal d( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeF lterPred cates. s emTypeTypea adResult( emTypeOpt)

  overr de def getUua em(
    ceTarget: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] =
    logEvent.searchDeta ls.flatMap(_.query).flatMap { query =>
      ceTarget. emType match {
        case So ( emType.User) =>
          getProf le dFromUser em(ceTarget).map { prof le d =>
             em.Typea ad nfo(
              Typea ad nfo(
                act onQuery = query,
                typea adAct on nfo =
                  Typea adAct on nfo.UserResult(UserResult(prof le d = prof le d))))
          }
        case So ( emType.Search) =>
          ceTarget.na .map { na  =>
             em.Typea ad nfo(
              Typea ad nfo(
                act onQuery = query,
                typea adAct on nfo = Typea adAct on nfo.Top cQueryResult(
                  Top cQueryResult(suggestedTop cQuery = na ))))
          }
        case _ => None
      }
    }
}
