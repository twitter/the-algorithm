package com.X.recos.graph_common

import com.X.finagle.stats.Counter
import com.X.graphjet.stats.{Counter => GraphCounter}

/**
 * FinagleCounterWrapper wraps X's Finagle Counter.
 *
 * This is because GraphJet is an openly available library which does not
 * depend on Finagle, but tracks stats using a similar interface.
 */
class FinagleCounterWrapper(counter: Counter) extends GraphCounter {
  def incr() = counter.incr()
  def incr(delta: Int) = counter.incr(delta)
}
