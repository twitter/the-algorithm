package com.tw ter.product_m xer.core.serv ce.transfor r_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Transfor r
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * For wrapp ng [[Transfor r]]s that are appl ed per-cand date
 *
 * Records a s ngle span for runn ng all t  components,
 * but stats per-component.
 */
@S ngleton
class PerCand dateTransfor rExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor {

  def arrow[ n, Out](
    transfor r: Transfor r[ n, Out],
    context: Executor.Context,
  ): Arrow[Seq[ n], Seq[Try[Out]]] = {
    val perCand dateArrow = wrapPerCand dateComponentW hExecutorBookkeep ngW houtTrac ng(
      context,
      transfor r. dent f er
    )(Arrow.map(transfor r.transform)).l ftToTry

    wrapComponentsW hTrac ngOnly(
      context,
      transfor r. dent f er
    )(Arrow.sequence(perCand dateArrow))
  }
}
