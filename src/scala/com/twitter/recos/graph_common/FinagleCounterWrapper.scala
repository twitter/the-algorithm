package com.twitter.recos.graph_common

import com.twitter.finagle.stats.Counter
import com.twitter.graphjet.stats.{Counter => GraphCounter}

/**
 * FinagleCounterWrapper wraps Twitter's Finagle Counter.
 *
 * This is because GraphJet is an openly available library which does not
 * depend on Finagle, but tracks stats using a similar interface.
 */
class FinagleCounterWrapper(counter: Counter) extends GraphCounter {
  def incr() = counter.incr()
  def incr(delta: Int) = counter.incr(delta)
}
