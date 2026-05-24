package com.twitter.frigate.pushservice.params

import com.twitter.util.tunable.TunableMap

object PushServiceTunableKeys {
  final val IbisQpsLimitTunableKey = TunableMap.Key[Int]("ibis2.qps.limit")
  final val NtabQpsLimitTunableKey = TunableMap.Key[Int]("ntab.qps.limit")
  final val TweetPerspectiveStoreQpsLimit = TunableMap.Key[Int]("tweetperspective.qps.limit")
}
