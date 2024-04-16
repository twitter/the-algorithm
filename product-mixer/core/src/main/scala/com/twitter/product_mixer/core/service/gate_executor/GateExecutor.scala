package com.tw ter.product_m xer.core.serv ce.gate_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.funct onal_component.gate.GateResult
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.Arrow. so
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on. mmutable.Queue

/**
 * A GateExecutor takes a Seq[Gate], executes t m all sequent ally, and
 * determ nes a f nal Cont nue or Stop dec s on.
 */
@S ngleton
class GateExecutor @ nject() (overr de val statsRece ver: StatsRece ver) extends Executor {

  pr vate val Cont nue = "cont nue"
  pr vate val Sk pped = "sk pped"
  pr vate val Stop = "stop"

  def arrow[Query <: P pel neQuery](
    gates: Seq[BaseGate[Query]],
    context: Executor.Context
  ): Arrow[Query, GateExecutorResult] = {

    val gateArrows = gates.map(get soArrowForGate(_, context))
    val comb nedArrow =  soArrowsSequent ally(gateArrows)

    Arrow
      .map { query: Query => (query, GateExecutorResult(Queue.empty)) }
      .andT n(comb nedArrow)
      .map {
        case (_, gateExecutorResult) =>
          // mater al ze t  Queue  nto a L st for faster future  erat ons
          GateExecutorResult(gateExecutorResult. nd v dualGateResults.toL st)
      }
  }

  /**
   * Each gate  s transfor d  nto a  so Arrow over (Quest, L st[GatewayResult]).
   *
   * T  arrow:
   * - Adapts t   nput and output types of t  underly ng Gate arrow (an [[ so[(Query, QueryResult)]])
   * - throws a [[StoppedGateExcept on]]  f [[GateResult.cont nue]]  s false
   * -  f  s not false, prepends t  current results to t  [[GateExecutorResult. nd v dualGateResults]] l st
   */
  pr vate def get soArrowForGate[Query <: P pel neQuery](
    gate: BaseGate[Query],
    context: Executor.Context
  ):  so[(Query, GateExecutorResult)] = {
    val broadcastStatsRece ver =
      Executor.broadcastStatsRece ver(context, gate. dent f er, statsRece ver)

    val cont nueCounter = broadcastStatsRece ver.counter(Cont nue)
    val sk ppedCounter = broadcastStatsRece ver.counter(Sk pped)
    val stopCounter = broadcastStatsRece ver.counter(Stop)

    val observedArrow = wrapComponentW hExecutorBookkeep ng(
      context,
      gate. dent f er,
      onSuccess = { gateResult: GateResult =>
        gateResult match {
          case GateResult.Cont nue => cont nueCounter. ncr()
          case GateResult.Sk pped => sk ppedCounter. ncr()
          case GateResult.Stop => stopCounter. ncr()
        }
      }
    )(gate.arrow)

    val  nputAdapted: Arrow[(Query, GateExecutorResult), GateResult] =
      Arrow
        .map[(Query, GateExecutorResult), Query] { case (query, _) => query }
        .andT n(observedArrow)

    val z pped = Arrow.z pW hArg( nputAdapted)

    // at each step, t  current `GateExecutorResult.cont nue` value  s correct for all already run gates
    val w hStoppedGatesAsExcept ons = z pped.map {
      case ((query, prev ousResults), currentResult)  f currentResult.cont nue =>
        Return(
          (
            query,
            GateExecutorResult(
              prev ousResults. nd v dualGateResults :+ ExecutedGateResult(
                gate. dent f er,
                currentResult))
          ))
      case _ => Throw(StoppedGateExcept on(gate. dent f er))
    }.lo rFromTry

    /**
     *   gat r stats before convert ng closed gates to except ons because a closed gate
     *  sn't a fa lure for t  gate,  s a normal behav or
     * but   do want to remap t  t  [[StoppedGateExcept on]] created because t  [[BaseGate]]  s closed
     * to t  correct [[com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure]],
     * so   remap w h [[wrapW hErrorHandl ng]]
     */
    wrapW hErrorHandl ng(context, gate. dent f er)(w hStoppedGatesAsExcept ons)
  }
}
