package com.X.frigate.pushservice.util

import com.X.finagle.stats.Counter

object FunctionalUtil {
  def incr[T](counter: Counter): T => T = { x =>
    {
      counter.incr()
      x
    }
  }
}
