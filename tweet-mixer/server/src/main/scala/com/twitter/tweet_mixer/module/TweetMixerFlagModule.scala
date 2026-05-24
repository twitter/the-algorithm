package com.twitter.tweet_mixer.module

import com.twitter.inject.TwitterModule

object TweetMixerFlagName {
  final val DarkTrafficFilterDeciderKey = "thrift.dark.traffic.filter.decider_key"
}

object TweetMixerFlagModule extends TwitterModule {
  import TweetMixerFlagName._

  flag[String](
    name = DarkTrafficFilterDeciderKey,
    default = "enable_dark_traffic_filter",
    help = "Dark traffic filter decider key"
  )
}
