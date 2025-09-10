package com.twitter.tweet_mixer.product.home_recommended_tweets.param

import com.twitter.timelines.configapi.FSBoundedParam

object HomeRecommendedTweetsParam {
  val SupportedClientFSName = "home_recommended_tweets_supported_client"

  // Cap for content before inserting deep retrieval u2i candidates
  object LightRankerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "home_recommended_tweets_light_ranker_max_results",
        default = 500,
        min = 50,
        max = 2000
      )

  // Default if a client does not include the requestMaxResults parameter in the request
  object DefaultRequestedMaxResultsParam
      extends FSBoundedParam[Int](
        name = "home_recommended_tweets_default_requested_max_results",
        default = 300,
        min = 1,
        max = 2000
      )

  // Maximum number of results that can be provided by the service
  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "home_recommended_tweets_server_max_results",
        default = 500,
        min = 50,
        max = 2000
      )

  val boundedIntFSOverrides = Seq(
    DefaultRequestedMaxResultsParam,
    ServerMaxResultsParam,
    LightRankerMaxResultsParam
  )
}
