package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object CandidateSourceParams {
  val booleanFSOverrides = Seq(EventsEnabled, TrendsVideoEnabled)

  val boundedDoubleFsOverrides = Seq(EventsIrrelevanceDownrank)

  object EventsEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_events_enabled",
        default = false
      )

  object EventsIrrelevanceDownrank
      extends FSBoundedParam[Double](
        name = "tweet_mixer_events_irrelevance_downrank",
        default = 0.5,
        min = 0.0,
        max = 10.0
      )

  object TrendsVideoEnabled
      extends FSParam[Boolean](
        name = "tweet_mixer_trends_video_enabled",
        default = false
      )
}
