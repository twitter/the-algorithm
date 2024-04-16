package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.logbase.thr ftscala.Cl entEventRece ver
 mport com.tw ter.logbase.thr ftscala.LogBase
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

abstract class BaseCl entEvent(act onType: Act onType) {
  def toUn f edUserAct on(logEvent: LogEvent): Seq[Un f edUserAct on] = {
    val logBase: Opt on[LogBase] = logEvent.logBase

    for {
      ed <- logEvent.eventDeta ls.toSeq
       ems <- ed. ems.toSeq
      ce em <-  ems
      eventT  stamp <- logBase.flatMap(getS ceT  stamp)
      uua em <- getUua em(ce em, logEvent)
       f  s emTypeVal d(ce em. emType)
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
  }

  def getUua em(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ em] = for (act onT et d <- ce em. d)
    y eld  em.T et nfo(
      Cl entEventCommonUt ls
        .getBas cT et nfo(act onT et d, ce em, logEvent.eventNa space))

  // default  mple ntat on f lters  ems of type t et
  // overr de  n t  subclass  mple ntat on to f lter  ems of ot r types
  def  s emTypeVal d( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeF lterPred cates. s emTypeT et( emTypeOpt)

  def getS ceT  stamp(logBase: LogBase): Opt on[Long] =
    logBase.cl entEventRece ver match {
      case So (Cl entEventRece ver.CesHttp) | So (Cl entEventRece ver.CesThr ft) =>
        logBase.dr ftAdjustedEventCreatedAtMs
      case _ => So (logBase.dr ftAdjustedEventCreatedAtMs.getOrElse(logBase.t  stamp))
    }
}
