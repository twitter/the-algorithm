package com.tw ter.un f ed_user_act ons.enr c r.hydrator
 mport com.google.common.ut l.concurrent.RateL m er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.un f ed_user_act ons.enr c r.FatalExcept on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.logg ng.Logg ng

abstract class AbstractHydrator(scopedStatsRece ver: StatsRece ver) extends Hydrator w h Logg ng {

  object StatsNa s {
    val Except ons = "except ons"
    val EmptyKeys = "empty_keys"
    val Hydrat ons = "hydrat ons"
  }

  pr vate val except onsCounter = scopedStatsRece ver.counter(StatsNa s.Except ons)
  pr vate val emptyKeysCounter = scopedStatsRece ver.counter(StatsNa s.EmptyKeys)
  pr vate val hydrat onsCounter = scopedStatsRece ver.counter(StatsNa s.Hydrat ons)

  // at most 1 log  ssage per second
  pr vate val rateL m er = RateL m er.create(1.0)

  pr vate def rateL m edLogError(e: Throwable): Un  =
     f (rateL m er.tryAcqu re()) {
      error(e.get ssage, e)
    }

  protected def safelyHydrate(
     nstruct on: Enr ch nt nstruct on,
    keyOpt: Enr ch ntKey,
    envelop: Enr ch ntEnvelop
  ): Future[Enr ch ntEnvelop]

  overr de def hydrate(
     nstruct on: Enr ch nt nstruct on,
    keyOpt: Opt on[Enr ch ntKey],
    envelop: Enr ch ntEnvelop
  ): Future[Enr ch ntEnvelop] = {
    keyOpt
      .map(key => {
        safelyHydrate( nstruct on, key, envelop)
          .onSuccess(_ => hydrat onsCounter. ncr())
          .rescue {
            case e: FatalExcept on => Future.except on(e)
            case e =>
              rateL m edLogError(e)
              except onsCounter. ncr()
              Future.value(envelop)
          }
      }).getOrElse({
        emptyKeysCounter. ncr()
        Future.value(envelop)
      })
  }
}
