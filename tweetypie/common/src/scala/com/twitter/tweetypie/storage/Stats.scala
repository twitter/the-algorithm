package com.tw ter.t etyp e.storage

 mport com.tw ter.f nagle.stats.StatsRece ver

object Stats {
  // T se two  thods below (addW dthStat and updatePerF eldQpsCounters) are called per RPC call for most AP s,
  // so   rely on t  stats rece ver that  s passed  n to t  l brary to do  mo zat on.

  pr vate[storage] def addW dthStat(
    rpcNa : Str ng,
    paramNa : Str ng,
    w dth:  nt,
    stats: StatsRece ver
  ): Un  =
    getStat(rpcNa , paramNa , stats).add(w dth)

  // Updates t  counters for each Add  onal f eld. T   dea  re  s to expose t  QPS for each
  // add  onal f eld
  pr vate[storage] def updatePerF eldQpsCounters(
    rpcNa : Str ng,
    f eld ds: Seq[F eld d],
    count:  nt,
    stats: StatsRece ver
  ): Un  = {
    f eld ds.foreach { f eld d => getCounter(rpcNa , f eld d, stats). ncr(count) }
  }

  pr vate def getCounter(rpcNa : Str ng, f eld d: F eld d, stats: StatsRece ver) =
    stats.scope(rpcNa , "f elds", f eld d.toStr ng).counter("count")

  pr vate def getStat(rpcNa : Str ng, paramNa : Str ng, stats: StatsRece ver) =
    stats.scope(rpcNa , paramNa ).stat("w dth")
}
