package com.X.frigate.pushservice.params

import com.X.util.tunable.TunableMap

object PushServiceTunableKeys {
  final val IbisQpsLimitTunableKey = TunableMap.Key[Int]("ibis2.qps.limit")
  final val NtabQpsLimitTunableKey = TunableMap.Key[Int]("ntab.qps.limit")
  final val TweetPerspectiveStoreQpsLimit = TunableMap.Key[Int]("tweetperspective.qps.limit")
}
