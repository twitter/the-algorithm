package com.X.simclustersann.modules

import com.google.common.util.concurrent.RateLimiter
import com.google.inject.Provides
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.simclustersann.common.FlagNames.RateLimiterQPS
import javax.inject.Singleton

object RateLimiterModule extends XModule {
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
