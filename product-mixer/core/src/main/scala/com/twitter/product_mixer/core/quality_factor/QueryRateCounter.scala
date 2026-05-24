package com.twitter.product_mixer.core.quality_factor

import com.twitter.util.Duration
import com.twitter.util.Stopwatch
import com.twitter.util.TokenBucket

/**
 * Query rate counter based on a leaky bucket. For more, see [[com.twitter.util.TokenBucket]].
 */
case class QueryRateCounter private[quality_factor] (
  queryRateWindow: Duration) {

  private val queryRateWindowInSeconds = queryRateWindow.inSeconds

  private val leakyBucket: TokenBucket =
    TokenBucket.newLeakyBucket(ttl = queryRateWindow, reserve = 0, nowMs = Stopwatch.timeMillis)

  def increment(count: Int): Unit = leakyBucket.put(count)

  def getRate(): Double = leakyBucket.count / queryRateWindowInSeconds
}
