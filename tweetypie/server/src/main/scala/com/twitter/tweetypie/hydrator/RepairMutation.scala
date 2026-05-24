package com.twitter.tweetypie
package hydrator

/**
 * A Mutation that will note all repairs that took place in the
 * supplied StatsReceiver, under the names in repairers.
 */
object RepairMutation {
  def apply[T](stats: StatsReceiver, repairers: (String, Mutation[T])*): Mutation[T] =
    Mutation.all(
      repairers.map {
        case (name, mutation) => mutation.countMutations(stats.counter(name))
      }
    )
}
