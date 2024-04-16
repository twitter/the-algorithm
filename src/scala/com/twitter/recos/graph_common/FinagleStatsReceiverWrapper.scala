package com.tw ter.recos.graph_common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.stats.{StatsRece ver => GraphStatsRece ver}

/**
 * F nagleStatsRece verWrapper wraps Tw ter's F nagle StatsRece ver.
 *
 * T   s because GraphJet  s an openly ava lable l brary wh ch does not
 * depend on F nagle, but tracks stats us ng a s m lar  nterface.
 */
case class F nagleStatsRece verWrapper(statsRece ver: StatsRece ver) extends GraphStatsRece ver {

  def scope(na space: Str ng) = new F nagleStatsRece verWrapper(statsRece ver.scope(na space))
  def counter(na : Str ng) = new F nagleCounterWrapper(statsRece ver.counter(na ))
}
