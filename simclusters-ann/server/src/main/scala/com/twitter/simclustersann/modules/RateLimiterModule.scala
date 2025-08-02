package com.twitter.simclustersann.modules

import com.google.common.util.concurrent.RateLimiter
import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.simclustersann.common.FlagNames.RateLimiterQPS
import javatwitter.inject.Singleton

object RateLimiterModule etwittertends TwitterModule {
  flag[Int](
    name = RateLimiterQPS,
    default = 1000,
    help = "The QPS allowed by the rate limiter."
  )

  @Singleton
  @Provides
  def providesRateLimiter(
    @Flag(RateLimiterQPS) rateLimiterQps: Int
  ): RateLimiter =
    RateLimiter.create(rateLimiterQps)
}
