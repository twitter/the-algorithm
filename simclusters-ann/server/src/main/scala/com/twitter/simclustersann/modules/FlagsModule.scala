package com.twitter.simclustersann.modules

import com.twitter.inject.TwitterModule
import com.twitter.simclustersann.common.FlagNames

object FlagsModule extends TwitterModule {

  flag[Int](
    name = FlagNames.ServiceTimeout,
    default = 40,
    help = "The threshold of Request Timeout"
  )

  flag[String](
    name = FlagNames.DarkTrafficFilterDeciderKey,
    default = "dark_traffic_filter",
    help = "Dark traffic filter decider key"
  )

  flag[String](
    name = FlagNames.CacheDest,
    default = "/s/cache/content_recommender_unified_v2",
    help = "Path to memcache service. Currently using CR uniform scoring cache"
  )

  flag[Int](
    name = FlagNames.CacheTimeout,
    default = 15,
    help = "The threshold of MemCache Timeout"
  )

  flag[Boolean](
    name = FlagNames.CacheAsyncUpdate,
    default = false,
    help = "Whether to enable the async update for the MemCache"
  )

  flag[Int](
    name = FlagNames.MaxTopTweetPerCluster,
    default = 200,
    help = "Maximum number of tweets to take per each simclusters"
  )

}
