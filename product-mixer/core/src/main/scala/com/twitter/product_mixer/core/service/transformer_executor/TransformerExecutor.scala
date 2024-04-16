package com.tw ter.product_m xer.core.serv ce.transfor r_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Transfor r
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.st ch.Arrow

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Transfor rExecutor @ nject() (overr de val statsRece ver: StatsRece ver) extends Executor {
  def arrow[ n, Out](
    transfor r: Transfor r[ n, Out],
    context: Executor.Context
  ): Arrow[ n, Out] = {
    wrapComponentW hExecutorBookkeep ng(
      context,
      transfor r. dent f er
    )(Arrow.map(transfor r.transform))
  }
}
