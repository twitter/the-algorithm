package com.twitter.recos.graph_common

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.graphjet.stats.{StatsReceiver => GraphStatsReceiver}

/**
 * FinagleStatsReceiverWrapper wraps Twitter's Finagle StatsReceiver.
 *
 * This is because GraphJet is an openly available library which does not
 * depend on Finagle, but tracks stats using a similar interface.
 */
case class FinagleStatsReceiverWrapper(statsReceiver: StatsReceiver) extends GraphStatsReceiver {

  def scope(namespace: String) = new FinagleStatsReceiverWrapper(statsReceiver.scope(namespace))
  def counter(name: String) = new FinagleCounterWrapper(statsReceiver.counter(name))
}
