package com.twitter.frigate.pushservice.config

import com.twitter.frigate.common.util.Etwitterperiments

object EtwitterperimentsWithStats {

  /**
   * Add an etwitterperiment here to collect detailed pushservice stats.
   *
   * ! Important !
   * Keep this set small and remove etwitterperiments when you don't need the stats anymore.
   */
  final val PushEtwitterperiments: Set[String] = Set(
    Etwitterperiments.MRAndroidInlineActionHoldback.etwitterptName,
  )
}
