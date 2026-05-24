package com.twitter.frigate.pushservice.util

import com.twitter.finagle.stats.Counter

object FunctionalUtil {
  def incr[T](counter: Counter): T => T = { x =>
    {
      counter.incr()
      x
    }
  }
}
