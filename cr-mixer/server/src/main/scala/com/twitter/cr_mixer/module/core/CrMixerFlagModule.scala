package com.twitter.cr_mixer.module.core

import com.twitter.inject.TwitterModule

object CrMixerFlagName {
  val SERVICE_FLAG = "cr_mixer.flag"
  val DarkTrafficFilterDeciderKey = "thrift.dark.traffic.filter.decider_key"
}

object CrMixerFlagModule extends TwitterModule {
  import CrMixerFlagName._

  flag[Boolean](name = SERVICE_FLAG, default = false, help = "This is a CR Mixer flag")

  flag[String](
    name = DarkTrafficFilterDeciderKey,
    default = "dark_traffic_filter",
    help = "Dark traffic filter decider key"
  )
}
