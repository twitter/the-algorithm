package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSParam

object EarlybirdInNetworkTweetsParams {

  object EarlybirdInNetworkTweetsEnabled
      extends FSParam[Boolean](
        name = "earlybird_in_network_tweets_enabled",
        default = false
      )
  val booleanFSOverrides = Seq(EarlybirdInNetworkTweetsEnabled)
}
