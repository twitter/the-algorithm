package com.twitter.home_mixer.product.list_tweets.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object ListTweetsParam {
  val SupportedClientFSName = "list_tweets_supported_client"

  object EnableAdsCandidatePipelineParam
      extends FSParam[Boolean](
        name = "list_tweets_enable_ads",
        default = false
      )

  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "list_tweets_server_max_results",
        default = 100,
        min = 1,
        max = 500
      )
}
