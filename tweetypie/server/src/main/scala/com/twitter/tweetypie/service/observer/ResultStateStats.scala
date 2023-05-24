package com.twitter.tweetypie
package service
package observer

import com.twitter.finagle.stats.StatsReceiver

/**
 * "Result State" is, for every singular tweet read, we categorize the tweet
 * result as a success or failure.
 * These stats enable us to track true TPS success rates.
 */
private[service] case class ResultStateStats(private val underlying: StatsReceiver) {
  private val stats = underlying.scope("result_state")
  private val successCounter = stats.counter("success")
  private val failedCounter = stats.counter("failed")

  def success(delta: Long = 1): Unit = successCounter.incr(delta)
  def failed(delta: Long = 1): Unit = failedCounter.incr(delta)
}
