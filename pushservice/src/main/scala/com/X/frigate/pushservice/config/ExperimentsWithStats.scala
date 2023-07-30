package com.X.frigate.pushservice.config

import com.X.frigate.common.util.Experiments

object ExperimentsWithStats {

  /**
   * Add an experiment here to collect detailed pushservice stats.
   *
   * ! Important !
   * Keep this set small and remove experiments when you don't need the stats anymore.
   */
  final val PushExperiments: Set[String] = Set(
    Experiments.MRAndroidInlineActionHoldback.exptName,
  )
}
