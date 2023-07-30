package com.X.product_mixer.core.quality_factor

import com.X.util.Duration
import com.X.util.Stopwatch
import com.X.util.TokenBucket

/**
 * Query rate counter based on a leaky bucket. For more, see [[com.X.util.TokenBucket]].
 */
case class QueryRateCounter private[quality_factor] (
  queryRateWindow: Duration) {

  private val queryRateWindowInSeconds = queryRateWindow.inSeconds

  private val leakyBucket: TokenBucket =
    TokenBucket.newLeakyBucket(ttl = queryRateWindow, reserve = 0, nowMs = Stopwatch.timeMillis)

  def increment(count: Int): Unit = leakyBucket.put(count)

  def getRate(): Double = leakyBucket.count / queryRateWindowInSeconds
}
