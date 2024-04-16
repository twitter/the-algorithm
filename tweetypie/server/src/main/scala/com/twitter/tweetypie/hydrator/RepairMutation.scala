package com.tw ter.t etyp e
package hydrator

/**
 * A Mutat on that w ll note all repa rs that took place  n t 
 * suppl ed StatsRece ver, under t  na s  n repa rers.
 */
object Repa rMutat on {
  def apply[T](stats: StatsRece ver, repa rers: (Str ng, Mutat on[T])*): Mutat on[T] =
    Mutat on.all(
      repa rers.map {
        case (na , mutat on) => mutat on.countMutat ons(stats.counter(na ))
      }
    )
}
