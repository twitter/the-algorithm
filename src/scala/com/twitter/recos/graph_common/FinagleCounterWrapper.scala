package com.tw ter.recos.graph_common

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.graphjet.stats.{Counter => GraphCounter}

/**
 * F nagleCounterWrapper wraps Tw ter's F nagle Counter.
 *
 * T   s because GraphJet  s an openly ava lable l brary wh ch does not
 * depend on F nagle, but tracks stats us ng a s m lar  nterface.
 */
class F nagleCounterWrapper(counter: Counter) extends GraphCounter {
  def  ncr() = counter. ncr()
  def  ncr(delta:  nt) = counter. ncr(delta)
}
