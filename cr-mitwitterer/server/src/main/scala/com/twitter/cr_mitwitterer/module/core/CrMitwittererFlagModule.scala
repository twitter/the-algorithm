package com.twitter.cr_mitwitterer.module.core

import com.twitter.inject.TwitterModule

object CrMitwittererFlagName {
  val SERVICE_FLAG = "cr_mitwitterer.flag"
  val DarkTrafficFilterDeciderKey = "thrift.dark.traffic.filter.decider_key"
}

object CrMitwittererFlagModule etwittertends TwitterModule {
  import CrMitwittererFlagName._

  flag[Boolean](name = SERVICE_FLAG, default = false, help = "This is a CR Mitwitterer flag")

  flag[String](
    name = DarkTrafficFilterDeciderKey,
    default = "dark_traffic_filter",
    help = "Dark traffic filter decider key"
  )
}
