package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.stats.{Counter,  tr cBu lder, StatsRece ver, StatsRece verProxy}

/**
 * A StatsRece ver that  n  al zes counters to zero.
 * Prov des a s mple wrapper that wraps a StatsRece ver w re w n us ng counters,
 * have t m auto  n  al ze to 0.
 * Unt l a counter performs  s f rst  ncr()  s returned as "undef ned",
 * wh ch  ans  f an alert  s set on that counter
 *   w ll result  n an error.
 * Anot r advantage  s to remove t  need to manually  n  al ze counters  n order
 * to overco  afore nt oned problem.
 * @param self - underly ng StatsRece ver
 */
class Counter n  al z ngStatsRece ver(protected val self: StatsRece ver)
    extends StatsRece verProxy {

  overr de def counter( tr cBu lder:  tr cBu lder): Counter = {
    val counter = self.counter( tr cBu lder)
    counter. ncr(0)
    counter
  }
}
