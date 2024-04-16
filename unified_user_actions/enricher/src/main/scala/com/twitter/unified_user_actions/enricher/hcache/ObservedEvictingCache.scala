package com.tw ter.un f ed_user_act ons.enr c r.hcac 

 mport com.tw ter.cac .FutureCac 
 mport com.tw ter.cac .FutureCac Proxy
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future
 mport scala.annotat on.nowarn

/**
 * Adds stats and reuse t  ma n log c of t  Ev ct ngCac .
 */
class ObservedEv ct ngCac [K, V](underly ng: FutureCac [K, V], scopedStatsRece ver: StatsRece ver)
    extends FutureCac Proxy[K, V](underly ng) {
   mport ObservedEv ct ngCac ._

  pr vate[t ] val getsCounter = scopedStatsRece ver.counter(StatsNa s.Gets)
  pr vate[t ] val setsCounter = scopedStatsRece ver.counter(StatsNa s.Sets)
  pr vate[t ] val h sCounter = scopedStatsRece ver.counter(StatsNa s.H s)
  pr vate[t ] val m ssesCounter = scopedStatsRece ver.counter(StatsNa s.M sses)
  pr vate[t ] val ev ct onsCounter = scopedStatsRece ver.counter(StatsNa s.Ev ct ons)
  pr vate[t ] val fa ledFuturesCounter = scopedStatsRece ver.counter(StatsNa s.Fa ledFutures)

  @nowarn("cat=unused")
  pr vate[t ] val cac S zeGauge = scopedStatsRece ver.addGauge(StatsNa s.S ze)(underly ng.s ze)

  pr vate[t ] def ev ctOnFa lure(k: K, f: Future[V]): Future[V] = {
    f.onFa lure { _ =>
      fa ledFuturesCounter. ncr()
      ev ct(k, f)
    }
    f //   return t  or g nal future to make ev ct(k, f) eas er to work w h.
  }

  overr de def set(k: K, v: Future[V]): Un  = {
    setsCounter. ncr()
    super.set(k, v)
    ev ctOnFa lure(k, v)
  }

  overr de def getOrElseUpdate(k: K)(v: => Future[V]): Future[V] = {
    getsCounter. ncr()

    var computeWasEvaluated = false
    def computeW hTrack ng: Future[V] = v.onSuccess { _ =>
      computeWasEvaluated = true
      m ssesCounter. ncr()
    }

    ev ctOnFa lure(
      k,
      super.getOrElseUpdate(k)(computeW hTrack ng).onSuccess { _ =>
         f (!computeWasEvaluated) h sCounter. ncr()
      }
    ). nterrupt ble()
  }

  overr de def get(key: K): Opt on[Future[V]] = {
    getsCounter. ncr()
    val value = super.get(key)
    value match {
      case So (_) => h sCounter. ncr()
      case _ => m ssesCounter. ncr()
    }
    value
  }

  overr de def ev ct(key: K, value: Future[V]): Boolean = {
    val ev cted = super.ev ct(key, value)
     f (ev cted) ev ct onsCounter. ncr()
    ev cted
  }
}

object ObservedEv ct ngCac  {
  object StatsNa s {
    val Gets = "gets"
    val H s = "h s"
    val M sses = "m sses"
    val Sets = "sets"
    val Ev ct ons = "ev ct ons"
    val Fa ledFutures = "fa led_futures"
    val S ze = "s ze"
  }

  /**
   * Wraps an underly ng FutureCac , ensur ng that fa led Futures that are set  n
   * t  cac  are ev cted later.
   */
  def apply[K, V](underly ng: FutureCac [K, V], statsRece ver: StatsRece ver): FutureCac [K, V] =
    new ObservedEv ct ngCac [K, V](underly ng, statsRece ver)
}
