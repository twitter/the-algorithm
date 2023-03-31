package com.twitter.ann.service.query_server.common.throttling

/**
 * A simple ring buffer that keeps track of long values over `window`.
 */
private[throttling] class WindowedStats(window: Int) {
  private[this] val buffer = new Array[Long](window)
  private[this] var index = 0
  private[this] var sumValue = 0L
  private[this] var count = 0

  def add(v: Long): Unit = {
    count = math.min(count + 1, window)
    val old = buffer(index)
    buffer(index) = v
    index = (index + 1) % window
    sumValue += v - old
  }

  def avg: Double = { sumValue.toDouble / count }
  def sum: Long = { sumValue }
}
