package com.twitter.home_mixer.module

import com.twitter.conversions.DurationOps.RichDuration
import com.twitter.home_mixer.param.HomeMixerFlagName
import com.twitter.inject.TwitterModule
import com.twitter.util.Duration

object HomeMixerFlagsModule extends TwitterModule {

  import HomeMixerFlagName._

  flag[Boolean](
    name = ScribeClientEventsFlag,
    default = false,
    help = "Toggles logging client events to Scribe"
  )

  flag[Boolean](
    name = ScribeServedCandidatesFlag,
    default = false,
    help = "Toggles logging served candidates to Scribe"
  )

  flag[Boolean](
    name = ScribeScoredCandidatesFlag,
    default = false,
    help = "Toggles logging scored candidates to Scribe"
  )

  flag[Boolean](
    name = ScribeFeaturesFlag,
    default = false,
    help = "Toggles logging served common features and candidates features to Scribe"
  )

  flag[String](
    name = DataRecordMetadataStoreConfigsYmlFlag,
    default = "mysql_timelines_ro_prod.yml",
    help = "The YML file that contains the necessary info for creating metadata store MySQL client."
  )

  flag[String](
    name = DarkTrafficFilterDeciderKey,
    default = "dark_traffic_filter",
    help = "Dark traffic filter decider key"
  )

  flag[Duration](
    TargetScoringLatency,
    700.millis,
    "Target scoring latency for Quality Factor"
  )
}
