package com.X.recos.graph_common

import com.X.finagle.stats.StatsReceiver
import com.X.graphjet.stats.{StatsReceiver => GraphStatsReceiver}

/**
 * FinagleStatsReceiverWrapper wraps X's Finagle StatsReceiver.
 *
 * This is because GraphJet is an openly available library which does not
 * depend on Finagle, but tracks stats using a similar interface.
 */
case class FinagleStatsReceiverWrapper(statsReceiver: StatsReceiver) extends GraphStatsReceiver {

  def scope(namespace: String) = new FinagleStatsReceiverWrapper(statsReceiver.scope(namespace))
  def counter(name: String) = new FinagleCounterWrapper(statsReceiver.counter(name))
}
