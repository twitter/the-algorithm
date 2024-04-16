package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.logbase.thr ftscala.LogBase
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}

abstract class BaseCTACl entEvent(act onType: Act onType)
    extends BaseCl entEvent(act onType = act onType) {

  overr de def toUn f edUserAct on(logEvent: LogEvent): Seq[Un f edUserAct on] = {
    val logBase: Opt on[LogBase] = logEvent.logBase
    val user dent f er: User dent f er = User dent f er(
      user d = logBase.flatMap(_.user d),
      guest dMarket ng = logBase.flatMap(_.guest dMarket ng))
    val uua em:  em =  em.Cta nfo(CTA nfo())
    val eventT  stamp = logBase.flatMap(getS ceT  stamp).getOrElse(0L)
    val ce em = LogEvent em.unsafeEmpty

    val productSurface: Opt on[ProductSurface] = ProductSurfaceUt ls
      .getProductSurface(logEvent.eventNa space)

    val event taData: Event tadata = Cl entEventCommonUt ls
      .getEvent tadata(
        eventT  stamp = eventT  stamp,
        logEvent = logEvent,
        ce em = ce em,
        productSurface = productSurface
      )

    Seq(
      Un f edUserAct on(
        user dent f er = user dent f er,
         em = uua em,
        act onType = act onType,
        event tadata = event taData,
        productSurface = productSurface,
        productSurface nfo =
          ProductSurfaceUt ls.getProductSurface nfo(productSurface, ce em, logEvent)
      ))
  }

}
