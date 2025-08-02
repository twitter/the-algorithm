package com.twitter.home_mitwitterer.product.list_tweets.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object ListTweetsParam {
  val SupportedClientFSName = "list_tweets_supported_client"

  object EnableAdsCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "list_tweets_enable_ads",
        default = false
      )

  object ServerMatwitterResultsParam
      etwittertends FSBoundedParam[Int](
        name = "list_tweets_server_matwitter_results",
        default = 100,
        min = 1,
        matwitter = 500
      )
}
