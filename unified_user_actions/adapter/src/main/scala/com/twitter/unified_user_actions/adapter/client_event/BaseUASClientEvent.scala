package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.logbase.thr ftscala.LogBase
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

abstract class BaseUASCl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {

  overr de def toUn f edUserAct on(logEvent: LogEvent): Seq[Un f edUserAct on] = {
    val logBase: Opt on[LogBase] = logEvent.logBase
    val ce em = LogEvent em.unsafeEmpty

    val uuaOpt: Opt on[Un f edUserAct on] = for {
      eventT  stamp <- logBase.flatMap(getS ceT  stamp)
      uua em <- getUua em(ce em, logEvent)
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
          ce em = ce em,
          productSurface = productSurface
        )

      Un f edUserAct on(
        user dent f er = user dent f er,
         em = uua em,
        act onType = act onType,
        event tadata = event taData,
        productSurface = productSurface,
        productSurface nfo =
          ProductSurfaceUt ls.getProductSurface nfo(productSurface, ce em, logEvent)
      )
    }

    uuaOpt match {
      case So (uua) => Seq(uua)
      case _ => N l
    }
  }

  overr de def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] = for {
    performanceDeta ls <- logEvent.performanceDeta ls
    durat on <- performanceDeta ls.durat onMs
  } y eld {
     em.Uas nfo(UAS nfo(t  SpentMs = durat on))
  }
}
