package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSParam

object UserLocationParams {
  val booleanFSOverrides = Seq(UserLocationEnabled)

  object UserLocationEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_user_location_enabled",
        default = false
      )
}
