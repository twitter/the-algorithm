package com.tw ter.un f ed_user_act ons.adapter.ema l_not f cat on_event

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter. b s.thr ftscala.Not f cat onScr be
 mport com.tw ter. b s.thr ftscala.Not f cat onScr beType
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Ema lNot f cat on nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.ProductSurface
 mport com.tw ter.un f ed_user_act ons.thr ftscala.ProductSurface nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T et nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.User dent f er

class Ema lNot f cat onEventAdapter
    extends AbstractAdapter[Not f cat onScr be, UnKeyed, Un f edUserAct on] {
   mport Ema lNot f cat onEventAdapter._
  overr de def adaptOneToKeyedMany(
     nput: Not f cat onScr be,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent( nput).map { e => (UnKeyed, e) }
}

object Ema lNot f cat onEventAdapter {

  def adaptEvent(scr be: Not f cat onScr be): Seq[Un f edUserAct on] = {
    Opt on(scr be).flatMap { e =>
      e.`type` match {
        case Not f cat onScr beType.Cl ck =>
          val t et dOpt = e.logBase.flatMap(Ema lNot f cat onEventUt ls.extractT et d)
          (t et dOpt, e. mpress on d) match {
            case (So (t et d), So ( mpress on d)) =>
              So (
                Un f edUserAct on(
                  user dent f er = User dent f er(user d = e.user d),
                   em =  em.T et nfo(T et nfo(act onT et d = t et d)),
                  act onType = Act onType.Cl entT etEma lCl ck,
                  event tadata = Ema lNot f cat onEventUt ls.extractEvent taData(e),
                  productSurface = So (ProductSurface.Ema lNot f cat on),
                  productSurface nfo = So (
                    ProductSurface nfo.Ema lNot f cat on nfo(
                      Ema lNot f cat on nfo(not f cat on d =  mpress on d)))
                )
              )
            case _ => None
          }
        case _ => None
      }
    }.toSeq
  }
}
