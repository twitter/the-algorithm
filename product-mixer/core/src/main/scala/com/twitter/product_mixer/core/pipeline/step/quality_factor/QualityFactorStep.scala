package com.tw ter.product_m xer.core.p pel ne.step.qual y_factor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorStatus
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * Qual y Factor bu ld ng step that bu lds up t  state snapshot for a map of conf gs.
 *
 * @param statsRece ver Stats Rece ver used to bu ld f nagle gauges for QF State
 *
 * @tparam Query P pel ne query model w h qual y factor status
 * @tparam State T  p pel ne state doma n model.
 */
case class Qual yFactorStep[
  Query <: P pel neQuery w h HasQual yFactorStatus,
  State <: HasQuery[Query, State]] @ nject() (
  statsRece ver: StatsRece ver)
    extends Step[
      State,
      Qual yFactorStepConf g,
      Any,
      Qual yFactorStepResult
    ] {
  overr de def  sEmpty(conf g: Qual yFactorStepConf g): Boolean =
    conf g.qual yFactorStatus.qual yFactorByP pel ne. sEmpty

  overr de def adapt nput(
    state: State,
    conf g: Qual yFactorStepConf g
  ): Any = ()

  overr de def arrow(
    conf g: Qual yFactorStepConf g,
    context: Executor.Context
  ): Arrow[Any, Qual yFactorStepResult] = {
    //   use prov deGauge so t se gauges l ve forever even w hout a reference.
    val currentValues = conf g.qual yFactorStatus.qual yFactorByP pel ne.map {
      case ( dent f er, qual yFactor) =>
        // QF  s a relat ve stat (s nce t  parent p pel ne  s mon or ng a ch ld p pel ne)
        val scopes = conf g.p pel ne dent f er.toScopes ++  dent f er.toScopes :+ "Qual yFactor"
        val currentValue = qual yFactor.currentValue.toFloat
        statsRece ver.prov deGauge(scopes: _*) {
          currentValue
        }
         dent f er -> currentValue
    }
    Arrow.value(Qual yFactorStepResult(currentValues))
  }

  overr de def updateState(
    state: State,
    executorResult: Qual yFactorStepResult,
    conf g: Qual yFactorStepConf g
  ): State = state.updateQuery(
    state.query.w hQual yFactorStatus(conf g.qual yFactorStatus).as nstanceOf[Query])
}

case class Qual yFactorStepConf g(
  p pel ne dent f er: Component dent f er,
  qual yFactorStatus: Qual yFactorStatus)

case class Qual yFactorStepResult(currentValues: Map[Component dent f er, Float])
    extends ExecutorResult
