package com.twitter.simclustersann.common

object FlagNames {

  /**
   * Global Settings
   */
  final val ServiceTimeout = "service.timeout"
  final val DarkTrafficFilterDeciderKey = "thrift.dark.traffic.filter.decider_key"

  /**
   * Cache Setting
   */
  final val CacheDest = "cache_module.dest"
  final val CacheTimeout = "cache_module.timeout"
  // Only turn on the async update when the SANN Cluster has the production taffic.
  final val CacheAsyncUpdate = "cache_module.async_update"

  /**
   * Warmup Settings
   */
  final val DisableWarmup = "warmup.disable"
  final val NumberOfThreads = "warmup.thread_number"
  final val RateLimiterQPS = "warmup.rate_limiter_qps"

  /**
   * Algorithm Parameters
   */
  final val MaxTopTweetPerCluster = "sim_clusters.ann.max_top_tweets_per_cluster"

}
