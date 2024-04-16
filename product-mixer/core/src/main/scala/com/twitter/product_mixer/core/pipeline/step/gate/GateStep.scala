package com.tw ter.product_m xer.core.p pel ne.step.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A gate step,   takes t  query and t  g ven gates and executes t m. Gates do not update state
 *  f t y return cont nue, and throw an except on  f any gate says stopped, thus no state changes
 * are expected  n t  step. T  [[NewP pel neArrowBu lder]] and [[P pel neStep]] handle short
 * c rcu  ng t  p pel ne's execut on  f t  throws.
 *
 * @param gateExecutor Gate Executor for execut ng t  gates
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam State T  p pel ne state doma n model.
 */
case class GateStep[Query <: P pel neQuery, State <: HasQuery[Query, State]] @ nject() (
  gateExecutor: GateExecutor)
    extends Step[State, Seq[BaseGate[Query]], Query, GateExecutorResult] {

  overr de def adapt nput(state: State, conf g: Seq[BaseGate[Query]]): Query = state.query

  overr de def arrow(
    conf g: Seq[BaseGate[Query]],
    context: Executor.Context
  ): Arrow[Query, GateExecutorResult] = gateExecutor.arrow(conf g, context)

  // Gate Executor  s a noop,  f   cont nues, t  state  sn't changed.  f   stops t  world,
  // an except on gets thrown.
  overr de def updateState(
     nput: State,
    executorResult: GateExecutorResult,
    conf g: Seq[BaseGate[Query]]
  ): State =  nput

  overr de def  sEmpty(conf g: Seq[BaseGate[Query]]): Boolean = conf g. sEmpty
}
