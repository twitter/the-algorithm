package com.twitter.tweet_mixer
package param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object PopGrokTopicTweetsParams {

  // Enables Pop Grok Topic tweets candidate source
  object PopGrokTopicTweetsEnable
      extends FSParam[Boolean](
        name = "popular_grok_topic_tweets_enabled",
        default = false
      )

  object MaxNumCandidates
      extends FSBoundedParam[Int](
        name = "popular_grok_topic_tweets_max_num_candidates",
        min = 1,
        max = 2000,
        default = 500
      )

  val booleanFSOverrides = Seq(PopGrokTopicTweetsEnable)

  val boundedIntFSOverrides = Seq(MaxNumCandidates)
}
